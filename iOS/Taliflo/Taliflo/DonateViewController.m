//
//  TLFDonateViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "DonateViewController.h"
#import "User.h"
#import "NavigationBarHelper.h"
#import "Alert.h"
#import <AFNetworking/AFNetworking.h>
#import "UserStore.h"
#import "NetworkHelper.h"
#import "PaymentViewController.h"

@interface DonateViewController ()
{

}

@property (nonatomic, strong) NSString *authToken;

@end

@implementation DonateViewController

static NSString *donationAmount;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Set view controller title
        //[TLFNavBarHelper configViewController:self withTitle:@"Donate"];
        self.title = @"Donate Now";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Populate views
    self.name.text = self.cause.companyName;
    
    // Styling
    [[self.btnDonate layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
    
    donationAmount = @"20.00";
}

#pragma mark Action listeners

- (IBAction)selectDonation
{
    if (_segmentedControl.selectedSegmentIndex == 0) {
        // Selected $20
        donationAmount = @"20.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 1) {
        // Selected $40
        donationAmount = @"40.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 2) {
        // Selected $60
        donationAmount = @"60.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 3) {
        // Selected $100
        donationAmount = @"100.00";
    }
}

- (IBAction)confirm
{
    // Set the confirm action
    void(^yesAction)(UIAlertAction *action);
    yesAction = ^void(UIAlertAction *action) {
        [self alertYestAction];
    };
    
    // Show alert
    [Alert alertForViewController:self withTitle:@"Are you sure?" message:@"Your credit card will be charged this amount immediately" yesActionHandler:yesAction cancelActionHandler:nil];
}

// Method to respond to the response from the alert view (iOS 7)
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        NSLog(@"Cancel button clicked");
    }
    
    if (buttonIndex == 1) {
        NSLog(@"Yes button clicked");
        [self alertYestAction];
    }
}

- (void)alertYestAction
{
    PaymentViewController *paymentVC = [[PaymentViewController alloc] init];
    paymentVC.title = [NSString stringWithFormat:@"Donate $%@", donationAmount];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:paymentVC];
    [self presentViewController:navigationController animated:YES completion:nil];
}

@end
