//
//  TLFLoginViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/15/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFLoginViewController.h"
#import "TLFColor.h"

@interface TLFLoginViewController ()

@end

@implementation TLFLoginViewController

// Variables to control animations
static CGPoint originalCenter;
static int next = 0;

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

- (IBAction)authenticate
{
    [self.view.window.rootViewController dismissViewControllerAnimated:YES completion:nil];
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
    if (next == 0) {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.35f];
        originalCenter = self.view.center;
        self.view.center = CGPointMake(originalCenter.x, originalCenter.y - 64);
        [UIView commitAnimations];
        
        next = 1;
    }
}

- (void)hideKeyboard
{
    next = 0;
    
    [self.email resignFirstResponder];
    [self.password resignFirstResponder];
    
    [self scrollViewBackToCenter];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{

    [textField resignFirstResponder];
    
    // "Next" pressed
    if (textField.tag == 0) {
        next = 1;
        
        [self.password becomeFirstResponder];
    }
    
    // "Go" pressed, start login
    if (textField.tag == 1) {
        next = 0;
        [self scrollViewBackToCenter];
        
        [self authenticate];
    }
    
    return YES;
}

@end
