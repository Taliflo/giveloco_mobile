//
//  TLFLoginViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/15/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomKeyboard.h"

@interface LoginViewController : UIViewController <UITextFieldDelegate>

@property (strong, nonatomic) IBOutlet UITextField *email;
@property (strong, nonatomic) IBOutlet UITextField *password;
@property (strong, nonatomic) IBOutlet UIButton *btnLogin;
@property (strong, nonatomic) IBOutlet UIButton *btnSignup;

@end
