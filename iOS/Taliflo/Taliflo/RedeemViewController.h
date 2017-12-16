//
//  TLFRedeemViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class User;

@interface RedeemViewController : UIViewController

@property (nonatomic) User *business;

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UILabel *address;
@property (strong, nonatomic) IBOutlet UIButton *btnRedeem;

@end
