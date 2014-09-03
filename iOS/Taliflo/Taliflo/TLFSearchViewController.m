//
//  TLFSearchController.m
//  Taliflo
//
//  Created by NR-Mac on 1/2/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFSearchViewController.h"
#import "TLFCauseStore.h"
#import "TLFBusinessStore.h"
#import "TLFUserCell.h"
#import "TLFColor.h"

@interface TLFSearchViewController ()

@property (nonatomic) NSMutableArray *unfiltered, *businesses, *causes;
@property (nonatomic) NSMutableArray *searchResults;
@property (nonatomic, strong) UISearchDisplayController *searchController;

@property (nonatomic, strong) UISegmentedControl *segmentedControl;

@end

@implementation TLFSearchViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    UINib *nib = [UINib nibWithNibName:@"TLFUserCell" bundle:nil];
    [self.tableView registerNib:nib forCellReuseIdentifier:@"TLFUserCell"];
    
    self.searchResults = [[NSMutableArray alloc] init];
    
    self.searchController = [[UISearchDisplayController alloc] init];
    self.searchController.searchResultsDataSource = self;
    
    TLFCauseStore *cs = [TLFCauseStore getInstance];
    self.causes = cs.causes;
    
    TLFBusinessStore *bs = [TLFBusinessStore getInstance];
    self.businesses = bs.businesses;
    
    // Add UISegmentedController to the navigation bar
    NSArray *segmentOptions = @[@"Causes", @"Businesses"];
    self.segmentedControl = [[UISegmentedControl alloc] initWithItems:segmentOptions];
    [self.segmentedControl addTarget:self action:@selector(segmentSelection) forControlEvents:UIControlEventValueChanged];
    self.navigationItem.titleView = self.segmentedControl;
    
    self.segmentedControl.selectedSegmentIndex = 0;
    self.unfiltered = self.causes;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)segmentSelection
{
    if (self.segmentedControl.selectedSegmentIndex == 0) {
        self.unfiltered = self.causes;
        [self.navigationItem.titleView setTintColor:[TLFColor talifloTiffanyBlue]];
    }
    
    if (self.segmentedControl.selectedSegmentIndex == 1) {
        self.unfiltered = self.businesses;
        [self.navigationItem.titleView setTintColor:[TLFColor talifloPurple]];
    }
    
    [self.tableView reloadData];
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
    if (tableView.tag == 1) {
        return [self.unfiltered count];
    } else {
        return [self.searchResults count];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 161.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellID = @"TLFUserCell";
    TLFUserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellID forIndexPath:indexPath];
    
    // Configure the cell...
    
    if (tableView.tag == 1) {
        cell.name.text = self.unfiltered[indexPath.row][@"company_name"];
        cell.summary.text = self.unfiltered[indexPath.row][@"summary"];
    } else {
        cell.name.text = self.searchResults[indexPath.row][@"company_name"];
        cell.summary.text = self.unfiltered[indexPath.row][@"summary"];
    }
    
    if (self.segmentedControl.selectedSegmentIndex == 0) cell.backgroundColor = [TLFColor talifloTiffanyBlue];
    if (self.segmentedControl.selectedSegmentIndex == 1) cell.backgroundColor = [TLFColor talifloPurple];
    
    return cell;
}

/*
#pragma mark - Table view delegate

// In a xib-based application, navigation from a table can be handled in -tableView:didSelectRowAtIndexPath:
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here, for example:
    // Create the next view controller.
    <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:<#@"Nib name"#> bundle:nil];
    
    // Pass the selected object to the new view controller.
    
    // Push the view controller.
    [self.navigationController pushViewController:detailViewController animated:YES];
}
*/

#pragma mark - Search display delegate methods

// This method loads a new table view to display search results
- (void)searchDisplayController:(UISearchDisplayController *)controller didLoadSearchResultsTableView:(UITableView *)tableView
{
    UINib *nib = [UINib nibWithNibName:@"TLFUserCell" bundle:nil];
    [tableView registerNib:nib forCellReuseIdentifier:@"TLFUserCell"];
}

// Called whenever changes occur in the search bar
- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString
{
    [self.searchResults removeAllObjects];
    
    if (searchString.length > 0) {
        NSPredicate *pred = [NSPredicate predicateWithFormat:@"%K beginswith[cd] %@", @"company_name", self.searchBar.text];
        NSArray *matches = [self.unfiltered filteredArrayUsingPredicate:pred];
        [self.searchResults addObjectsFromArray:matches];
    }
    
    return YES;
}

@end
