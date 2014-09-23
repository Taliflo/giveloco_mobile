//
//  TLFLoginViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/15/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFLoginViewController.h"
#import "TLFColor.h"
#import "TLFAppDelegate.h"
#import <AFNetworking/AFHTTPSessionManager.h>
#import "TLFAlert.h"
#import "TLFUserStore.h"
#import "TLFUser.h"
#import "TLFNetworkHelper.h"
#import "TLFRegisterViewController.h"

@interface TLFLoginViewController ()

@end

@implementation TLFLoginViewController

// Variables to control animations
static CGPoint originalCenter;
static BOOL screenSize3point5 = NO;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    CGRect screenRect = [[UIScreen mainScreen] bounds];
    CGFloat screenHeight = screenRect.size.height;
    
    if (screenHeight == 480) {
        screenSize3point5 = YES;
    }
    
    [TLFColor setStrokeTB:self.email];
    [TLFColor setStrokeTB:self.password];
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

        AFHTTPSessionManager *manager = [TLFNetworkHelper newSessionManager:nil];
        
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
                  TLFUserStore *userStore = [TLFUserStore getInstance];
                  [userStore setLoggedInCredentials:responseObject];
                  
                  // Start the rest of the application
                  TLFAppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
                  [appDelegate startApplicationAfterLogin];
            
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            NSLog(@"%@", error.localizedDescription);
            [TLFAlert shakeTextField:self.password];
        }];
 
    }
}

- (IBAction)openSignup
{
    // Create a new TLFRegisterViewController
    TLFRegisterViewController *registerVC = [[TLFRegisterViewController alloc] init];
    
    // Wrap it in a vagiation controller
    UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController:registerVC];
    
    [self presentViewController:navController animated:YES completion:nil];
}

- (void)scrollViewUp
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.35f];
    originalCenter = self.view.center;
    self.view.center = CGPointMake(originalCenter.x, originalCenter.y - 24);
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
    if (screenSize3point5) {
        [self scrollViewUp];
    }
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (screenSize3point5) {
        [self scrollViewBackToCenter];
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
        [self.password resignFirstResponder];
        [self attemptLogin];
    }
    
    return YES;
}

@end
