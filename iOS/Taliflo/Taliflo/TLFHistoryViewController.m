//
//  TLFHistoryViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFHistoryViewController.h"
#import "TLFColor.h"
#import "TLFNavBarConfig.h"

@implementation TLFHistoryViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
        // Setting the title and tab bar title and image
        [TLFNavBarConfig configViewController:self
                                    withTitle:@"History"
                                    withImage:[UIImage imageNamed:@"History.png"]];
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the navigation bar style
    [TLFNavBarConfig configViewController:self
                         withBarTintColor:[UIColor whiteColor]
                            withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

@end
