//
//  TLFBusinessesViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/3/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "BusinessesViewController.h"
#import "NavigationBarHelper.h"
#import "CustomColor.h"
#import "UserCell.h"
#import "BusinessesViewController.h"
#import "NetworkHelper.h"
#import "UserDetailViewController.h"
#import "User.h"

@interface BusinessesViewController ()

@property (nonatomic, strong) NSMutableArray *filtered;
@property (nonatomic, strong) NSMutableArray *businesses;

@end

static NavigationBarHelper *nbHelper;
static NetworkHelper *networkHelper;
static NSString *cellID = @"UserCell";
static NSString *sysCellID = @"UITableViewCell";

@implementation BusinessesViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Setting the navigation bar title, and the tab bar title and icon
        //[TLFNavBarHelper configViewController:self withTabBarTitle:@"Businesses" withIcon:[UIImage imageNamed:@"Businesses.png"]];
        nbHelper = [[NavigationBarHelper alloc] initWithViewController:self title:@"Businesses"];
        [NavigationBarHelper configViewController:self
                              withTabBarTitle:@"Businesses"
                                     withIcon:[UIImage imageNamed:@"Businesses.png"]];
        
        // Request businesses        
        self.businesses = [[NSMutableArray alloc] init];
        self.filtered = [[NSMutableArray alloc] init];
        [NetworkHelper requestUsers:@"business" forTableViewController:self backingList:self.businesses];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Load and register the table view cell nib file
    UINib *nib = [UINib nibWithNibName:cellID bundle:nil];
    [self.tableView registerNib:nib forCellReuseIdentifier:cellID];
    
    // Register system table view cell for the search display table view
    [self.searchDisplayController.searchResultsTableView registerClass:[UITableViewCell class] forCellReuseIdentifier:sysCellID];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // Setting the tab bar selected item colour
    //self.tabBarController.tabBar.selectedImageTintColor = [TLFColor talifloPurple];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    if (nbHelper.actionMenu.isOpen)
        [nbHelper.actionMenu close];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        return [self.filtered count];
    } else {
        return [self.businesses count];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (tableView == self.searchDisplayController.searchResultsTableView)
        return 40.0f;
    else
        return 160.0f;
}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wincompatible-pointer-types"

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:sysCellID forIndexPath:indexPath];
        
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:sysCellID];
        }
        
        cell.textLabel.text = [self.filtered[indexPath.row] companyName];
        return cell;
    } else {
        UserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellID forIndexPath:indexPath];
        
        if (cell == nil) {
            cell = [[UserCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
        }
        
        cell.name.text = [self.businesses[indexPath.row] companyName];
        cell.summary.text = [self.businesses[indexPath.row] summary];
        cell.backgroundColor = [CustomColor talifloPurple];
        return cell;
    }
}

#pragma clang diagnostic pop

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UserDetailViewController *detailVC = [[UserDetailViewController alloc] init];
    
    // Pass the selected business to the user detail view controller
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        detailVC.user = self.filtered[indexPath.row];
    } else {
        detailVC.user = self.businesses[indexPath.row];
    }
    
    // Push the view controller.
    [self.navigationController pushViewController:detailVC animated:YES];
}

#pragma mark - Search display delegate

// This method is called whenever changes occur in the search bar
- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString
{
    [self.filtered removeAllObjects];
    
    if (searchString.length > 0) {
        NSPredicate *predName = [NSPredicate predicateWithFormat:@"%K beginswith[cd] %@", @"company_name", self.searchBar.text];
        NSArray *hits = [self.businesses filteredArrayUsingPredicate:predName];
        [self.filtered addObjectsFromArray:hits];
    }
    
    return YES;
}

@end
