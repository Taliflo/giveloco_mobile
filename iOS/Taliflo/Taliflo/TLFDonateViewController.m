//
//  TLFDonateViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFDonateViewController.h"
#import "TLFUser.h"
#import "TLFNavBarHelper.h"

@interface TLFDonateViewController () {

}

@end

@implementation TLFDonateViewController

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

#pragma mark Action listeners

- (IBAction)selectDonation
{
    if (_segmentedControl.selectedSegmentIndex == 0) {
        // Selected $20
    }
    
    if (_segmentedControl.selectedSegmentIndex == 1) {
        // Selected $40
    }
    
    if (_segmentedControl.selectedSegmentIndex == 2) {
        // Selected $60
    }
    
    if (_segmentedControl.selectedSegmentIndex == 3) {
        // Selected $100
    }
}

- (IBAction)confirm
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"Your credit card will be charged this amount immediately." delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
    [alert show];
    
    // To be used on iOS 8 for backwards compatability
/*    if ([UIAlertController class]) {
        
    } else {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"Your credit card will be charged this amount immediately." delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
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
