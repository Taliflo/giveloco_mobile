//
//  TLFUserDetailViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/28/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class User;

@interface UserDetailViewController : UIViewController

@property (strong, nonatomic) IBOutlet UILabel *companyName;
@property (strong, nonatomic) IBOutlet UILabel *tags;
@property (strong, nonatomic) IBOutlet UILabel *availableCredit;
@property (strong, nonatomic) IBOutlet UIButton *btnTransact;
@property (strong, nonatomic) IBOutlet UIButton *btnSupport;
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) IBOutlet UIImageView *image;
@property (strong, nonatomic) IBOutlet UITextView *descript;
@property (strong, nonatomic) IBOutlet UILabel *address;
@property (strong, nonatomic) IBOutlet UITextView *phone;
@property (strong, nonatomic) IBOutlet UILabel *redeemDisabledMsg;

@property (strong, nonatomic) User *user;

@end
