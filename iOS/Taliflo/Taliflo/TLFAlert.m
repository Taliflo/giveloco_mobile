//
//  TLFAlert.m
//  Taliflo
//
//  Created by NR-Mac on 1/19/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFAlert.h"
#import "UITextField+Shake.h"

@implementation TLFAlert

// Error alert
+ (void)alertForViewController:(UIViewController *)viewController forError:(NSError *)error withTitle:(NSString *)title
{
    // Check for iOS 8
    if ([UIAlertController class]) {
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:title
                                                                       message:[NSString stringWithFormat:@"%@. Try again later.", [error localizedDescription]]
                                                                preferredStyle:UIAlertControllerStyleAlert];
        
        UIAlertAction *ok = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:nil];
        
        [alert addAction:ok];
        [viewController presentViewController:alert animated:YES completion:nil];
    } else {
        UIAlertView *alertView = [[UIAlertView alloc]
                                  initWithTitle:title
                                  message:[NSString stringWithFormat:@"%@. Try again later.", [error localizedDescription]]
                                  delegate:nil
                                  cancelButtonTitle:@"Ok"
                                  otherButtonTitles:nil];
        
        [alertView show];
    }
}

// Yes - Cancel alert
+ (void)alertForViewController:(UIViewController *)viewController
                     withTitle:(NSString *)title
                       message:(NSString *)message
              yesActionHandler:(void (^)(UIAlertAction *action))yesAction
               cancelActionHandler:(void (^)(UIAlertAction *action))cancelAction
{
    // Check for iOS 8
    if ([UIAlertController class]) {
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:title message:message preferredStyle:UIAlertControllerStyleAlert];
        
        UIAlertAction *cancel = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:cancelAction];
        UIAlertAction *yes = [UIAlertAction actionWithTitle:@"Yes" style:UIAlertActionStyleDefault handler:yesAction];
        
        [alert addAction:cancel];
        [alert addAction:yes];
        [viewController presentViewController:alert animated:YES completion:nil];
    } else {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
        [alert show];
    }
}

+ (void) okAlertForViewController:(UIViewController *)viewController withTitle:(NSString *)title message:(NSString *)message
{
    // Check for iOS 8
    if ([UIAlertController class]) {
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:title
                                                                       message:message
                                                                preferredStyle:UIAlertControllerStyleAlert];
        
        UIAlertAction *ok = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:nil];
        
        [alert addAction:ok];
        [viewController presentViewController:alert animated:YES completion:nil];
    } else {
        UIAlertView *alertView = [[UIAlertView alloc]
                                  initWithTitle:title
                                  message:message
                                  delegate:nil
                                  cancelButtonTitle:@"Ok"
                                  otherButtonTitles:nil];
        
        [alertView show];
    }
}

+ (void)shakeTextField:(UITextField *)textField
{
    [textField shake:10
           withDelta:5.
            andSpeed:0.04
      shakeDirection:ShakeDirectionHorizontal
          completion:^{
              textField.text = @"";
          }];
}

@end
