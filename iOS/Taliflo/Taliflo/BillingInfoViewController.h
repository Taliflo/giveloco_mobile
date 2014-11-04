//
//  TLFBillingInfoViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomKeyboard.h"

@interface BillingInfoViewController : UIViewController
    <UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate, CustomKeyboardDelegate, UIAlertViewDelegate>

@property (strong, nonatomic) IBOutlet UITextField *name;
@property (strong, nonatomic) IBOutlet UITextField *number;
@property (strong, nonatomic) IBOutlet UITextField *expiryDate;
@property (strong, nonatomic) IBOutlet UITextField *cvv;
@property (strong, nonatomic) IBOutlet UITextField *street;
@property (strong, nonatomic) IBOutlet UITextField *city;
@property (strong, nonatomic) IBOutlet UITextField *zip;
@property (strong, nonatomic) IBOutlet UITextField *state;
@property (strong, nonatomic) IBOutlet UITextField *country;
@property (strong, nonatomic) IBOutlet UIButton *btnSave;

@end
