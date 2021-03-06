//
//  TLFCausesViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/3/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "CausesViewController.h"
#import "NavigationBarHelper.h"
#import "CustomColor.h"
#import "UserCell.h"
#import "CauseStore.h"
#import "NetworkHelper.h"
#import "UserDetailViewController.h"
#import "User.h"

@interface CausesViewController ()

@property (nonatomic, strong) NSMutableArray *filtered;
@property (nonatomic, strong) NSMutableArray *causes;

@end

static NavigationBarHelper *nbHelper;
static NetworkHelper *networkHelper;
static NSString *cellID = @"UserCell";
static NSString *sysCellID = @"UITableViewCell";

@implementation CausesViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Setting the navigation bar title, and the tab bar title and icon
        //[TLFNavBarHelper configViewController:self withTabBarTitle:@"Causes" withIcon:[UIImage imageNamed:@"Causes.png"]];
        
        nbHelper = [[NavigationBarHelper alloc] initWithViewController:self title:@"Causes"];
        [NavigationBarHelper configViewController:self
                              withTabBarTitle:@"Causes"
                                     withIcon:[UIImage imageNamed:@"Causes.png"]];
        
        // Request causes
        self.causes = [[NSMutableArray alloc] init];
        self.filtered = [[NSMutableArray alloc] init];
        [NetworkHelper requestUsers:@"cause" forTableViewController:self backingList:self.causes];
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
    //self.tabBarController.tabBar.selectedImageTintColor = [TLFColor talifloTiffanyBlue];
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
        return [self.causes count];
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
        
        cell.name.text = [self.causes[indexPath.row] companyName];
        cell.summary.text = [self.causes[indexPath.row] summary];
        cell.backgroundColor = [CustomColor talifloTiffanyBlue];
        return cell;
    }
}

#pragma mark diagnostic pop


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UserDetailViewController *detailVC = [[UserDetailViewController alloc] init];
    
    // Pass the selected cause to the user detail view controller
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        detailVC.user = self.filtered[indexPath.row];
    } else {
        detailVC.user = self.causes[indexPath.row];
    }
    
    // Push the view controller.
    [self.navigationController pushViewController:detailVC animated:YES];
}

#pragma mark - Search display delegate

// This method loads a new table view to display search results
- (void)searchDisplayController:(UISearchDisplayController *)controller didLoadSearchResultsTableView:(UITableView *)tableView
{
}

- (void)searchDisplayControllerWillBeginSearch:(UISearchDisplayController *)controller
{
}

// This method is called whenever changes occur in the search bar
- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString
{
    [self.filtered removeAllObjects];
    
    if (searchString.length > 0) {
        NSPredicate *predName = [NSPredicate predicateWithFormat:@"%K beginswith[cd] %@", @"company_name", self.searchBar.text];
        NSArray *hits = [self.causes filteredArrayUsingPredicate:predName];
        [self.filtered addObjectsFromArray:hits];
    }
 
    return YES;
}

- (void)searchDisplayControllerWillEndSearch:(UISearchDisplayController *)controller
{
}


@end
