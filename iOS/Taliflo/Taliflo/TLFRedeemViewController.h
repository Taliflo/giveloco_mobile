//
//  TLFRedeemViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TLFUser;

@interface TLFRedeemViewController : UIViewController

@property (nonatomic) TLFUser *business;

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UILabel *address;

@end
