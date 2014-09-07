//
//  TLFRedeemViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRedeemViewController.h"
#import "TLFUser.h"
#import "TLFNavBarHelper.h"

@interface TLFRedeemViewController ()

@end

@implementation TLFRedeemViewController

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
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"Your available balance will be debited this amount immediately." delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
    [alert show];
    
    // To be used on iOS 8 for backwards compatability
    /*    if ([UIAlertController class]) {
     
     } else {
     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"Your available balance will be debited this amount immediately." delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
     [alert show];
     
     } */
}

// Method to respond to the response from the alert view
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) NSLog(@"Cancel button clicked");
    if (buttonIndex == 1) NSLog(@"Yes button clicked");
}

@end
