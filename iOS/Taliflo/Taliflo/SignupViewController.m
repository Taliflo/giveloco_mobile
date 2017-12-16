//
//  TLFRegisterViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/22/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "SignupViewController.h"
#import "NetworkHelper.h"
#import "CustomColor.h"
#import "AppDelegate.h"
#import "UserStore.h"
#import "Alert.h"
#import "TranslateViewHelper.h"

@interface SignupViewController ()

@end

@implementation SignupViewController

static TranslateViewHelper *viewHelper;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        // Set left navigation bar button
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]
                                                 initWithBarButtonSystemItem:UIBarButtonSystemItemCancel
                                                 target:self action:@selector(dismiss)];
        
        viewHelper = [[TranslateViewHelper alloc] initWithViewController:self];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.navigationController.navigationBar.tintColor = [CustomColor talifloTiffanyBlue];
    
    NSRange range = [self.labelTaC.text rangeOfString:@"Terms and Conditions"];
    [self.labelTaC addLinkToURL:[NSURL URLWithString:@"http://www.google.ca/" ] withRange:range];
    
    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyboard)];
    [self.view addGestureRecognizer:tapGesture];
    
    // Styling
    [[self.btnSignup layer] setCornerRadius:3];
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
    if ([self.firstName.text isEqualToString:@""]){ [Alert shakeTextField:self.firstName]; emptyField = YES; }
    if ([self.lastName.text isEqualToString:@""]){ [Alert shakeTextField:self.lastName]; emptyField = YES; }
    if ([self.email.text isEqualToString:@""]){ [Alert shakeTextField:self.email]; emptyField = YES; }
    if ([self.password.text isEqualToString:@""]){ [Alert shakeTextField:self.password]; emptyField = YES; }
    
    if (emptyField) {
        return;
    }
    
    // Handling of too short password
    if ([self.password.text length] < 8) {
        // Show alert
        [Alert okAlertForViewController:self withTitle:@"Password Too Short" message:@"Your password must be 8 characters, or more, in length."];
        self.password.text = @"";
        return;
    }
    
    AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:nil];
    
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
              UserStore *userStore = [UserStore getInstance];
              [userStore setLoggedInCredentials:responseObject];
              
              // Dismiss view controller
              [self dismiss];
              
              // Start the rest of the application
              AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
              [appDelegate startApplication];
          }
          failure:^(NSURLSessionDataTask *task, NSError *error) {
              NSLog(@"Signup Failed: %@", [error localizedDescription]);
          }];
}

#pragma mark - Text field delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (viewHelper.screenSize4s && textField.tag == 3) {
        [viewHelper scrollViewUp:44];
    }
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (textField.tag == 3) {
        [viewHelper scrollViewBackToCenter];
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
        
        if (viewHelper.screenSize4s) {
            [viewHelper scrollViewBackToCenter];
        }
    }
    
    return YES;
}

- (void)hideKeyboard
{
    [self.view endEditing:YES];
}

@end
