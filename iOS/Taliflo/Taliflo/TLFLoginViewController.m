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
#import "TLFAlert.h"
#import "TLFUserStore.h"
#import "TLFUser.h"
#import "TLFNetworkHelper.h"
#import "TLFSignupViewController.h"
#import "TLFViewHelper.h"

@interface TLFLoginViewController ()

@end

@implementation TLFLoginViewController

// Variables to control animations
static TLFViewHelper *viewHelper;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        viewHelper = [[TLFViewHelper alloc] initWithViewController:self];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
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
        
        [self.view endEditing:YES];

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
                  [appDelegate startApplication];
            
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            NSLog(@"%@", error.localizedDescription);
            [TLFAlert shakeTextField:self.password];
        }];
 
    }
}

- (IBAction)openSignup
{
    // Create a new TLFRegisterViewController
    TLFSignupViewController *registerVC = [[TLFSignupViewController alloc] init];
    
    // Wrap it in a vagiation controller
    UINavigationController *navController = [[UINavigationController alloc] initWithRootViewController:registerVC];
    
    [self presentViewController:navController animated:YES completion:nil];
}

#pragma mark - Text field delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (viewHelper.screenSizeSmall) {
        [viewHelper scrollViewUp:-1];
    }
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (viewHelper.screenSizeSmall) {
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
