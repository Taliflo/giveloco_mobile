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
    NSLog(@"Selected User: %@", _user.role);
    
    TLFNavBarHelper *nbHelper = [TLFNavBarHelper getInstance];
    NSString *title = [[NSString alloc] init];
    if ([_user.role isEqualToString:@"business"]) title = @"View Business";
    if ([_user.role isEqualToString:@"cause"]) title = @"View Cause";
    [nbHelper configViewController:self withTitle:title];
    
    _companyName.text = _user.companyName;
    _description.text = _user.description;
    _tags.text = [_user getTagsString];
    _address.text = [NSString
                     stringWithFormat:@"%@\n%@, %@\n%@", _user.streetAddress, _user.city, _user.state, _user.zip];
    _phone.text = _user.phone;
    
    TLFUserStore *userStore = [TLFUserStore getInstance];
    _availableCredit.text = [NSString stringWithFormat:@"C %@", userStore.currentUser.balance];
    
    // ** Styling **
    
    if ([_user.role isEqualToString:bussIden]) {
        [_btnSupport setTitle:[_user getSupportedCausesStr]
                     forState:UIControlStateNormal];
        [_btnTransact setBackgroundColor:[TLFColor talifloPurple]];
        [_btnTransact setTitle:@"Redeem $20" forState:UIControlStateNormal];
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        [_btnSupport setTitle:[_user getSupportersCountStr]
                     forState:UIControlStateNormal];
        [_btnTransact setBackgroundColor:[TLFColor talifloTiffanyBlue]];
        [_btnTransact setTitle:@"Donate" forState:UIControlStateNormal];
    }
    
    [[_btnTransact layer] setCornerRadius:3];
    [[_btnSupport layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
        redeemVC.business = _user;
        [self.navigationController pushViewController:redeemVC animated:YES];
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        // Open donate view controller
        TLFDonateViewController *donateVC = [[TLFDonateViewController alloc] init];
        donateVC.cause = _user;
        [self.navigationController pushViewController:donateVC animated:YES];
    }
    
}

- (IBAction)openSupport:(id)sender
{
    NSLog(@"Touched btnSupport");
    
    // Open UserSupport view controller
    TLFUserSupportViewController *userSupportVC = [[TLFUserSupportViewController alloc] init];
    
    if ([_user.role isEqualToString:bussIden]) {
        NSLog(@"%@", _user.supportedCauses);
        userSupportVC.support = _user.supportedCauses;
        userSupportVC.title = @"Supported Causes";
        userSupportVC.supportRole = @"cause";
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        NSLog(@"%@", _user.supporters);
        userSupportVC.support = _user.supporters;
        userSupportVC.title = @"Supporting Businesses";
        userSupportVC.supportRole = @"business";
    }
    
    // Push the UserSupport view controller to the top of the current navigation stack
    [self.navigationController pushViewController:userSupportVC animated:YES];
}

@end
