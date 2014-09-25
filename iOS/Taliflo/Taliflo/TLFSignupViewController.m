//
//  TLFRegisterViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/22/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFSignupViewController.h"
#import "TLFNetworkHelper.h"
#import "TLFColor.h"
#import "TLFAppDelegate.h"
#import "TLFUserStore.h"
#import "TLFAlert.h"

@interface TLFSignupViewController ()

@end

@implementation TLFSignupViewController

static BOOL screenSize3Point5 = NO;
static CGPoint originalCenter;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Set left navigation bar button
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]
                                                 initWithBarButtonSystemItem:UIBarButtonSystemItemCancel
                                                 target:self action:@selector(dismiss)];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.navigationController.navigationBar.tintColor = [TLFColor talifloTiffanyBlue];
    
    CGRect screenRect = [[UIScreen mainScreen] bounds];
    CGFloat screenHeight = screenRect.size.height;
    
    if (screenHeight == 480) {
        screenSize3Point5 = YES;
    }
    
    NSRange range = [self.labelTaC.text rangeOfString:@"Terms and Conditions"];
    [self.labelTaC addLinkToURL:[NSURL URLWithString:@"http://www.google.ca/" ] withRange:range];
    
    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                                 action:@selector(hideKeyboard)];
    [self.view addGestureRecognizer:tapGesture];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dismiss
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)attemptSignup
{
    // Handling of empty fields
    BOOL emptyField = NO;
    if ([self.firstName.text isEqualToString:@""]){ [TLFAlert shakeTextField:self.firstName]; emptyField = YES; }
    if ([self.lastName.text isEqualToString:@""]){ [TLFAlert shakeTextField:self.lastName]; emptyField = YES; }
    if ([self.email.text isEqualToString:@""]){ [TLFAlert shakeTextField:self.email]; emptyField = YES; }
    if ([self.password.text isEqualToString:@""]){ [TLFAlert shakeTextField:self.password]; emptyField = YES; }
    
    if (emptyField) {
        return;
    }
    
    // Handling of too short password
    if ([self.password.text length] < 8) {
        // Show alert
        [TLFAlert okAlertForViewController:self withTitle:@"Password Too Short" message:@"Your password must be 8 characters, or more, in length."];
        self.password.text = @"";
        return;
    }
    
    AFHTTPSessionManager *manager = [TLFNetworkHelper newSessionManager:nil];
    
    NSDictionary *newUser = @{
                             @"role": @"individual",
                             @"first_name": self.firstName.text,
                             @"last_name": self.lastName.text,
                             @"email":self.email.text,
                             @"password":self.password.text,
                             @"company_name":[NSNull null],
                             @"website":[NSNull null],
                             @"street_address":[NSNull null],
                             @"country":[NSNull null],
                             @"state":[NSNull null],
                             @"zip":[NSNull null],
                             @"phone":[NSNull null],
                             @"tags":[NSNull null],
                             @"description":[NSNull null],
                             @"summary":[NSNull null]
                             };
    
    NSDictionary *params = @{@"user":newUser};
    
    [manager POST:@"user/signup"
        parameters:params
          success:^(NSURLSessionDataTask *task, id responseObject) {
              NSLog(@"Signup Response: %@", responseObject);
              
              // Set as logged in user
              TLFUserStore *userStore = [TLFUserStore getInstance];
              [userStore setLoggedInCredentials:responseObject];
              
              // Dismiss view controller
              [self dismiss];
              
              // Start the rest of the application
              TLFAppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
              [appDelegate startApplicationAfterLogin];
          }
          failure:^(NSURLSessionDataTask *task, NSError *error) {
              NSLog(@"Signup Failed: %@", [error localizedDescription]);
          }];
}

- (void)scrollViewUp
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.35f];
    originalCenter = self.view.center;
    self.view.center = CGPointMake(originalCenter.x, originalCenter.y - 44);
    [UIView commitAnimations];
}

- (void)scrollViewBackToCenter
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.35f];
    self.view.center = originalCenter;
    [UIView commitAnimations];
}

#pragma mark - Text field delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (screenSize3Point5 && textField.tag == 3) {
        [self scrollViewUp];
    }
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (textField.tag == 3) {
        [self scrollViewBackToCenter];
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    
    if (textField.tag == 0) {
        [self.lastName becomeFirstResponder];
    }
    
    if (textField.tag == 1) {
        [self.email becomeFirstResponder];
    }
    
    if (textField.tag == 2) {
        [self.password becomeFirstResponder];
    }
    
    if (textField.tag == 3) {
        [textField resignFirstResponder];
        
        if (screenSize3Point5) {
            [self scrollViewBackToCenter];
        }
    }
    
    return YES;
}

- (void)hideKeyboard
{
    [self.view endEditing:YES];
}

@end
