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
        [TLFNavBarHelper configViewController:self withTitle:@"Donate"];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Populate views
    _name.text = _cause.companyName;
    
    // Styling
    [[_btnDonate layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark Action listeners

- (IBAction)selectDonation
{
    if (_segmentedControl.selectedSegmentIndex) {
        // Selected $20
    }
    
    if (_segmentedControl.selectedSegmentIndex) {
        // Selected $40
    }
    
    if (_segmentedControl.selectedSegmentIndex) {
        // Selected $60
    }
    
    if (_segmentedControl.selectedSegmentIndex) {
        // Selected $100
    }
}

- (IBAction)donate:(id)sender
{
    
}

@end
