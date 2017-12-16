//
//  TLFLoginViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/15/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "LoginViewController.h"
#import "CustomColor.h"
#import "AppDelegate.h"
#import "Alert.h"
#import "UserStore.h"
#import "User.h"
#import "NetworkHelper.h"
#import "SignupViewController.h"
#import "TranslateViewHelper.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

// Variables to control animations
static TranslateViewHelper *viewHelper;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        viewHelper = [[TranslateViewHelper alloc] initWithViewController:self];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [CustomColor setStrokeTB:self.email];
    [CustomColor setStrokeTB:self.password];
    [[self.btnLogin layer] setCornerRadius:3];

    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc]
                                          initWithTarget:self action:@selector(hideKeyboard)];
    [self.view addGestureRecognizer:tapGesture];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)attemptLogin
{
    if (![self.password.text isEqual:@""]) {
        
        [self.view endEditing:YES];

        AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:nil];
        
        NSDictionary *params = @{
                                 @"email": self.email.text,
                                 @"password": self.password.text
                                 };
        
        [manager POST:@"user/login"
           parameters:params
              success:^(NSURLSessionDataTask *task, id responseObject) {
                  NSLog(@"Login Response: %@", responseObject);
                  
                  self.password.text = @"";
                  
                  // Set ID of logged in user
                  UserStore *userStore = [UserStore getInstance];
                  [userStore setLoggedInCredentials:responseObject];
                  
                  // Start the rest of the application
                  AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
                  [appDelegate startApplication];
            
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            NSLog(@"%@", error.localizedDescription);
            [Alert shakeTextField:self.password];
        }];
 
    }
}

- (IBAction)openSignup
{
    // Create a new TLFRegisterViewController
    SignupViewController *registerVC = [[SignupViewController alloc] init];
    
    // Wrap it in a vagiation controller
    UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController:registerVC];
    
    [self presentViewController:navController animated:YES completion:nil];
}

#pragma mark - Text field delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (viewHelper.screenSize4s) {
        [viewHelper scrollViewUp:-1];
    }
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (viewHelper.screenSize4s) {
        [viewHelper scrollViewBackToCenter];
    }
}

- (void)hideKeyboard
{
    [self.view endEditing:YES];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{

    [textField resignFirstResponder];
    
    // "Next" pressed
    if (textField.tag == 0) {
        [self.password becomeFirstResponder];
    }
    
    // "Go" pressed, start login
    if (textField.tag == 1) {
        [self attemptLogin];
    }
    
    return YES;
}

@end
