//
//  TLFUserDetailViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/28/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFUserDetailViewController.h"
#import "TLFUser.h"
#import "TLFUserStore.h"
#import "TLFColor.h"
#import "TLFUserSupportViewController.h"
#import "TLFRedeemViewController.h"
#import "TLFDonateViewController.h"
#import "TLFNavBarHelper.h"

@interface TLFUserDetailViewController ()

@end

static TLFNavBarHelper *nbHelper;
static NSString *causeIden = @"cause";
static NSString *bussIden = @"business";

@implementation TLFUserDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Populate and style layout views based on role
    NSLog(@"Selected User: %@", self.user.role);
    
    NSString *title = [[NSString alloc] init];
    if ([self.user.role isEqualToString:@"business"]) title = @"View Business";
    if ([self.user.role isEqualToString:@"cause"]) title = @"View Cause";
    nbHelper = [[TLFNavBarHelper alloc] initWithViewController:self title:title];
    
    self.companyName.text = self.user.companyName;
    self.description.text = self.user.description;
    self.tags.text = [self.user getTagsString];
    self.address.text = [NSString
                     stringWithFormat:@"%@\n%@, %@\n%@", self.user.streetAddress, self.user.city, self.user.state, self.user.zip];
    self.phone.text = self.user.phone;
    
    TLFUserStore *userStore = [TLFUserStore getInstance];
    self.availableCredit.text = [NSString stringWithFormat:@"C %@", userStore.currentUser.balance];
    
    // ** Styling **
    
    if ([self.user.role isEqualToString:bussIden]) {
        [self.btnSupport setTitle:[_user getSupportedCausesStr]
                     forState:UIControlStateNormal];
        [self.btnTransact setBackgroundColor:[TLFColor talifloPurple]];
        [self.btnTransact setTitle:@"Redeem $20" forState:UIControlStateNormal];
    }
    
    if ([self.user.role isEqualToString:causeIden]) {
        [self.btnSupport setTitle:[_user getSupportersCountStr]
                     forState:UIControlStateNormal];
        [self.btnTransact setBackgroundColor:[TLFColor talifloTiffanyBlue]];
        [self.btnTransact setTitle:@"Donate" forState:UIControlStateNormal];
    }
    
    [[self.btnTransact layer] setCornerRadius:3];
    [[self.btnSupport layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    if (nbHelper.actionMenu.isOpen)
        [nbHelper.actionMenu close];
}

- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
    [self.scrollView setContentSize:CGSizeMake(320, 1000)];
}

#pragma mark Button listeners

- (IBAction)transact:(id)sender
{
    NSLog(@"Touched btnTransact");
    
    
    if ([_user.role isEqualToString:bussIden]) {
        // Open redeem view controller
        TLFRedeemViewController *redeemVC = [[TLFRedeemViewController alloc] init];
        redeemVC.business = self.user;
        [self.navigationController pushViewController:redeemVC animated:YES];
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        // Open donate view controller
        TLFDonateViewController *donateVC = [[TLFDonateViewController alloc] init];
        donateVC.cause = self.user;
        [self.navigationController pushViewController:donateVC animated:YES];
    }
    
}

- (IBAction)openSupport:(id)sender
{
    NSLog(@"Touched btnSupport");
    
    // Open UserSupport view controller
    TLFUserSupportViewController *userSupportVC = [[TLFUserSupportViewController alloc] init];
    
    if ([_user.role isEqualToString:bussIden]) {
        NSLog(@"%@", self.user.supportedCauses);
        userSupportVC.support = self.user.supportedCauses;
        userSupportVC.title = @"Supported Causes";
        userSupportVC.supportRole = @"cause";
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        NSLog(@"%@", self.user.supporters);
        userSupportVC.support = self.user.supporters;
        userSupportVC.title = @"Supporting Businesses";
        userSupportVC.supportRole = @"business";
    }
    
    // Push the UserSupport view controller to the top of the current navigation stack
    [self.navigationController pushViewController:userSupportVC animated:YES];
}

@end
