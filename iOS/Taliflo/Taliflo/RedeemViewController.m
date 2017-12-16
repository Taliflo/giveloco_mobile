//
//  TLFRedeemViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "RedeemViewController.h"
#import "User.h"
#import "NavigationBarHelper.h"
#import "Alert.h"

@interface RedeemViewController ()

@end

@implementation RedeemViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        //self.title = @"Redeem Credits";

    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // ** Styling **

    // Set view controller title
    //[TLFNavBarHelper configViewController:self withTitle:@"Redeem Credits"];
    
    // Populate views
    self.name.text = self.business.companyName;
    self.address.text = [NSString
                stringWithFormat:@"%@\n%@, %@", self.business.streetAddress, self.business.city, self.business.state];
    
    // Styling
    [[self.btnRedeem layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)confirm
{
    // Set confirm action
    void (^yesAction)(UIAlertAction *action);
    yesAction = ^void(UIAlertAction *action) {
        NSLog(@"\"Yes\" selected");
    };
    
    // Show alert
    [Alert alertForViewController:self withTitle:@"Are you sure?" message:@"Your available balance will be debited the amount immediately." yesActionHandler:yesAction cancelActionHandler:nil];
}

// Method to respond to the response from the alert view
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) NSLog(@"Cancel button clicked");
    if (buttonIndex == 1) NSLog(@"Yes button clicked");
}

@end
