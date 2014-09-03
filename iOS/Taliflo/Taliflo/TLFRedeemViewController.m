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
    [TLFNavBarHelper configViewController:self withTitle:@"Redeem Credits"];
    
    // Populate views
    _name.text = _business.companyName;
    _address.text = [NSString
                stringWithFormat:@"%@\n%@, %@", _business.streetAddress, _business.city, _business.state];
    
    // Styling
    [[_btnRedeem layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
