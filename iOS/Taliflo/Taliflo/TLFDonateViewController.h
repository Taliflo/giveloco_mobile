//
//  TLFDonateViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Stripe.h"

@class TLFUser;

@interface TLFDonateViewController : UIViewController <PKPaymentAuthorizationViewControllerDelegate>

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentedControl;
@property (strong, nonatomic) IBOutlet UISwitch *monthlyRecurringSwitch;
@property (strong, nonatomic) IBOutlet UIButton *btnDonate;

@property (nonatomic) TLFUser *cause;

@end
