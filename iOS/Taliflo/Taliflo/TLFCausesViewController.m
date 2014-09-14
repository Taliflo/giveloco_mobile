//
//  TLFCausesViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/3/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFCausesViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFColor.h"
#import "TLFUserCell.h"
#import "TLFCauseStore.h"
#import "TLFRestHelper.h"
#import "TLFUserDetailViewController.h"

@interface TLFCausesViewController ()

@property (nonatomic, strong) NSMutableArray *filtered;

@end

static TLFNavBarHelper *nbHelper;
static TLFRestHelper *restHelper;
static NSString *cellID = @"TLFUserCell";
static NSString *sysCellID = @"UITableViewCell";

@implementation TLFCausesViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Setting the navigation bar title, and the tab bar title and icon
        //[TLFNavBarHelper configViewController:self withTabBarTitle:@"Causes" withIcon:[UIImage imageNamed:@"Causes.png"]];
        
        nbHelper = [[TLFNavBarHelper alloc] initWithViewController:self title:@"Causes"];
        [TLFNavBarHelper configViewController:self
                              withTabBarTitle:@"Causes"
                                     withIcon:[UIImage imageNamed:@"Causes.png"]];
        
        // Request causes
        restHelper = [[TLFRestHelper alloc] initWithTableViewController:self];
        [restHelper requestUsers:@"cause"];
        
        self.filtered = [[NSMutableArray alloc] init];

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
    self.tabBarController.tabBar.selectedImageTintColor = [TLFColor talifloTiffanyBlue];
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
        return [restHelper.users count];
    }

}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (tableView == self.searchDisplayController.searchResultsTableView)
        return 40.0f;
    else
        return 161.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:sysCellID forIndexPath:indexPath];
        cell.textLabel.text = self.filtered[indexPath.row][@"company_name"];
        return cell;
    } else {
        TLFUserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellID forIndexPath:indexPath];
        cell.name.text = restHelper.users[indexPath.row][@"company_name"];
        cell.summary.text = restHelper.users[indexPath.row][@"summary"];
        cell.backgroundColor = [TLFColor talifloTiffanyBlue];
        return cell;
    }
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TLFUserDetailViewController *detailVC = [[TLFUserDetailViewController alloc] init];
    
    // Pass the selected cause to the user detail view controller
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        detailVC.user = [[TLFUser alloc] initWithDictionary:self.filtered[indexPath.row]];
    } else {
        detailVC.user = [[TLFUser alloc] initWithDictionary:restHelper.users[indexPath.row]];
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
        NSArray *hits = [restHelper.users filteredArrayUsingPredicate:predName];
        [self.filtered addObjectsFromArray:hits];
    }
 
    return YES;
}

- (void)searchDisplayControllerWillEndSearch:(UISearchDisplayController *)controller
{
}


@end
