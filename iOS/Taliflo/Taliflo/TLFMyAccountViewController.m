//
//  TLFMyAccountViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>

#import "TLFMyAccountViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFColor.h"

@interface TLFMyAccountViewController ()

@end

static TLFNavBarHelper *helper;


@implementation TLFMyAccountViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Setting the nav bar title, and the tab bar title and image
        helper = [TLFNavBarHelper getInstance];
        [helper configViewController:self withTitle:@"My Account" withImage:[UIImage imageNamed:@"MyAccount.png"]];
        
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the nav bar style
    [helper configViewController:self withBarTintColor:[UIColor whiteColor] withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [[self.balance layer] setBorderColor:[[TLFColor talifloTiffanyBlue] CGColor]];
    [[self.balance layer] setBorderWidth:2.0];
    [[self.balance layer] setCornerRadius:3];
    CGRect frame = _balance.frame;
    frame.size.height = _balance.frame.size.height + 10;
    _balance.frame = frame;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
