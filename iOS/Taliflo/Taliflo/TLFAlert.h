//
//  TLFAlert.h
//  Taliflo
//
//  Created by NR-Mac on 1/19/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFAlert : NSObject

+ (void)alertForViewController:(UIViewController *)viewController forError:(NSError *)error withTitle:(NSString *)title;

+ (void)alertForViewController:(UIViewController *)viewController
                     withTitle:(NSString *)title
                       message:(NSString *)message
              yesActionHandler:(void (^)(UIAlertAction *action))yesAction
               cancelActionHandler:(void (^)(UIAlertAction *action))cancelAction;

+ (void) okAlertForViewController:(UIViewController *)viewController withTitle:(NSString *)title message:(NSString *)message;

+ (void)shakeTextField:(UITextField *)textField;

@end
