//
//  TLFUserDetailViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/28/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "UserDetailViewController.h"
#import "User.h"
#import "UserStore.h"
#import "CustomColor.h"
#import "UserSupportViewController.h"
#import "RedeemViewController.h"
#import "DonateViewController.h"
#import "NavigationBarHelper.h"

#define causeIden @"cause"
#define bussIden @"business"

@interface UserDetailViewController ()

@end

static NavigationBarHelper *nbHelper;
static BOOL redeemableBusiness = NO;

@implementation UserDetailViewController

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
    nbHelper = [[NavigationBarHelper alloc] initWithViewController:self title:title];
    
    self.companyName.text = self.user.companyName;
    self.descript.text = self.user.descript;
    self.tags.text = [self.user getTagsString];
    self.address.text = [NSString
                     stringWithFormat:@"%@\n%@, %@\n%@", self.user.streetAddress, self.user.city, self.user.state, self.user.zip];
    self.phone.text = self.user.phone;
    
    UserStore *userStore = [UserStore getInstance];
    self.availableCredit.text = [NSString stringWithFormat:@"C %@", userStore.currentUser.balance];
    
    // ** Styling **
    int xpos = [[UIScreen mainScreen] bounds].size.width - 8 - 55;
    
    UITextField *supportCount = [[UITextField alloc] initWithFrame:CGRectMake(xpos, 5, 35, 25)];
    [[supportCount layer] setCornerRadius:5];
    supportCount.backgroundColor = [CustomColor talifloOrange];
    supportCount.textColor = [UIColor whiteColor];
    supportCount.text = @"10";
    supportCount.textAlignment = NSTextAlignmentCenter;
    supportCount.font = [UIFont fontWithName:@"Helvetica" size:15];
    [self.btnSupport addSubview:supportCount];
    [self.btnSupport setTitleEdgeInsets:UIEdgeInsetsMake(0, 10, 0, 0)];
    self.btnSupport.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
    
    if ([self.user.role isEqualToString:bussIden]) {
        [self.btnSupport setTitle:@"Supported Causes"
                     forState:UIControlStateNormal];
        [self.btnTransact setBackgroundColor:[CustomColor talifloPurple]];
        [self.btnTransact setTitle:@"Redeem $20" forState:UIControlStateNormal];
        supportCount.text =[NSString stringWithFormat:@"%i", [self.user getSupportedCausesCount]];
        
        // Check if this business is a redeemable business
        redeemableBusiness = [userStore.currentUser checkReemableBusiness:self.user];
        redeemableBusiness = NO;
        NSLog(redeemableBusiness ? @"redeemableBusiness = YES" : @"redeemableBusiness = NO" );
        
        if (redeemableBusiness) {
            [self.redeemDisabledMsg removeFromSuperview];
        } else {
            self.btnTransact.enabled = NO;
            self.btnTransact.backgroundColor = [CustomColor disabledGrey];
        }
    }
    
    if ([self.user.role isEqualToString:causeIden]) {
        [self.btnSupport setTitle:@"Supporters"
                     forState:UIControlStateNormal];
        [self.btnTransact setBackgroundColor:[CustomColor talifloTiffanyBlue]];
        [self.btnTransact setTitle:@"Donate" forState:UIControlStateNormal];
        supportCount.text =[NSString stringWithFormat:@"%i", [self.user getSupportersCount]];
        
        [self.redeemDisabledMsg removeFromSuperview];
    }
    
    [[self.btnTransact layer] setCornerRadius:3];
    [[self.btnSupport layer] setCornerRadius:3];
    [self.btnSupport setNeedsLayout];
    
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
    [self.scrollView setContentSize:CGSizeMake(320, 736)];
}

#pragma mark Button listeners

- (IBAction)transact:(id)sender
{
    NSLog(@"Touched btnTransact");
    
    
    if ([_user.role isEqualToString:bussIden]) {
        // Open redeem view controller
        RedeemViewController *redeemVC = [[RedeemViewController alloc] init];
        redeemVC.business = self.user;
        [self.navigationController pushViewController:redeemVC animated:YES];
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        // Open donate view controller
        DonateViewController *donateVC = [[DonateViewController alloc] init];
        donateVC.cause = self.user;
        [self.navigationController pushViewController:donateVC animated:YES];
    }
    
}

- (IBAction)openSupport:(id)sender
{
    NSLog(@"Touched btnSupport");
    
    // Open UserSupport view controller
    UserSupportViewController *userSupportVC = [[UserSupportViewController alloc] init];
    
    if ([_user.role isEqualToString:bussIden]) {
        NSLog(@"%@", self.user.supportedCauses);
        userSupportVC.support = [self.user getSupportedCauses];
        userSupportVC.title = @"Supported Causes";
        userSupportVC.supportRole = @"cause";
    }
    
    if ([_user.role isEqualToString:causeIden]) {
        NSLog(@"%@", self.user.supporters);
        userSupportVC.support = [self.user getSupporters];
        userSupportVC.title = @"Supporting Businesses";
        userSupportVC.supportRole = @"business";
    }
    
    // Push the UserSupport view controller to the top of the current navigation stack
    [self.navigationController pushViewController:userSupportVC animated:YES];
}

@end
