//
//  TLFRedeemCreditsViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRedeemCreditsViewController.h"
#import "TLFUser.h"
#import "TLFUserStore.h"
#import "TLFUserCell.h"
#import "TLFColor.h"
#import "TLFRedeemViewController.h"

@interface TLFRedeemCreditsViewController ()

@end

static NSString *cellID = @"TLFUserCell";

@implementation TLFRedeemCreditsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Select a Business";
        self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:nil action:@selector(close)];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Load and register the table view cell nib file
    UINib *nib = [UINib nibWithNibName:cellID bundle:nil];
    [self.tableView registerNib:nib forCellReuseIdentifier:cellID];
    
    TLFUserStore *userStore = [TLFUserStore getInstance];
    self.currentUser = userStore.currentUser;
    
    NSLog(@"%@", self.currentUser.redeemableBusinesses);
    
    if (self.currentUser.redeemableBusinesses.count != 0) {
        [self.noneMsg removeFromSuperview];
    } else {
        [self.tableView removeFromSuperview];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)close
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.currentUser.redeemableBusinesses.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 161.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TLFUserCell *cell = [tableView dequeueReusableCellWithIdentifier:cellID forIndexPath:indexPath];
    
    if (cell == nil) {
        cell = [[TLFUserCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
    }
    
    cell.name.text = [self.currentUser.redeemableBusinesses[indexPath.row] companyName];
    cell.summary.text = [self.currentUser.redeemableBusinesses[indexPath.row] summary];
    cell.backgroundColor = [TLFColor talifloPurple];
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TLFRedeemViewController *redeemVC = [[TLFRedeemViewController alloc] init];
    
    // Pass the selected business to the user detail view controller
    redeemVC.business = [[TLFUser alloc] initWithJSON:self.currentUser.redeemableBusinesses[indexPath.row]];
    
    // Push the view controller.
    [self.navigationController pushViewController:redeemVC animated:YES];
}

@end
