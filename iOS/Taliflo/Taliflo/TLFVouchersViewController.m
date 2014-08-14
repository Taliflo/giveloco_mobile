//
//  TLFVouchersViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFVouchersViewController.h"
#import "TLFNavBarConfig.h"
#import "TLFColor.h"

@implementation TLFVouchersViewController

- (instancetype)init
{
    self = [super initWithStyle:UITableViewStylePlain];
    
    if (self) {
        
        // Setting the title and tab bar title and image
        [TLFNavBarConfig configViewController:self
                                    withTitle:@"My Vouchers"
                                    withImage:[UIImage imageNamed:@"Voucher.png"]];
    }
    
    return self;
}

- (instancetype)initWithStyle:(UITableViewStyle)style
{
    return [self init];
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
