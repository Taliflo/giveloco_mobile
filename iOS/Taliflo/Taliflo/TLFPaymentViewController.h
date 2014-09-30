//
//  TLFPaymentViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomKeyboard.h"

@interface TLFPaymentViewController : UIViewController <UITextFieldDelegate, CustomKeyboardDelegate>

@property (strong, nonatomic) IBOutlet UITextField *cardNumber;
@property (strong, nonatomic) IBOutlet UITextField *expiryDate;
@property (strong, nonatomic) IBOutlet UITextField *cvv;

@end
