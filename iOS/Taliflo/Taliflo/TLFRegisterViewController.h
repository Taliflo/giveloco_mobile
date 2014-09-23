//
//  TLFRegisterViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/22/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTTAttributedLabel.h"

@interface TLFRegisterViewController : UIViewController <UITextFieldDelegate, TTTAttributedLabelDelegate>

@property (strong, nonatomic) IBOutlet UITextField *firstName;
@property (strong, nonatomic) IBOutlet UITextField *lastName;
@property (strong, nonatomic) IBOutlet UITextField *email;
@property (strong, nonatomic) IBOutlet UITextField *password;
@property (strong, nonatomic) IBOutlet TTTAttributedLabel *labelTaC;
@property (strong, nonatomic) IBOutlet UIButton *btnSignup;

@end
