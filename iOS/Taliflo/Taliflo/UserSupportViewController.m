//
//  TLFUserSupportViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "UserSupportViewController.h"
#import "NavigationBarHelper.h"
#import "UserCell.h"
#import "CustomColor.h"
#import "NavigationBarHelper.h"
#import "UserDetailViewController.h"
#import "User.h"


@interface UserSupportViewController ()

@end

static NavigationBarHelper *nbHelper;
static NSString *cellName = @"TLFUserCell";
static NavigationBarHelper *nbHelper;

@implementation UserSupportViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        nbHelper = [[NavigationBarHelper alloc] initWithViewController:self];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    [self.tableView setBackgroundColor:[CustomColor lightestGrey]];
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:cellName bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:cellName];
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
    return [_support count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 161.0f;
}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wincompatible-pointer-types"

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellName forIndexPath:indexPath];
    
    if (cell == nil) {
        cell = [[UserCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellName];
    }
    
    // Configure the cell...
    
    cell.name.text = [self.support[indexPath.row] companyName];
    cell.summary.text = [self.support[indexPath.row] summary];
    
    if ([self.supportRole isEqualToString:@"cause"])
        cell.backgroundColor = [CustomColor talifloTiffanyBlue];
    
    if ([self.supportRole isEqualToString:@"business"])
        cell.backgroundColor = [CustomColor talifloPurple];
    
    return cell;
}

#pragma clang diagnostic pop

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UserDetailViewController *detailVC = [[UserDetailViewController alloc] init];
    
    // Pass the selected cause to the user detail view controller
    detailVC.user = [[User alloc] initWithJSON:self.support[indexPath.row]];
    
    // Push the view controller.
    [self.navigationController pushViewController:detailVC animated:YES];
}

@end
