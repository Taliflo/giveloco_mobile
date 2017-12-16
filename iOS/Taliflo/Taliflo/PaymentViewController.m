//
//  TLFPaymentViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/29/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "PaymentViewController.h"
#import "Stripe.h"
#import "Alert.h"
#import "NetworkHelper.h"

@interface PaymentViewController ()

@property (nonatomic, strong) CustomKeyboard *customKeyboard;

@end

@implementation PaymentViewController

static BOOL screenSize3Point5 =  NO;
static CGPoint originalCenter;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismiss)];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyboard)];
    [self.view addGestureRecognizer:tapGesture];
    
    // Keyboard toolbar
    self.customKeyboard = [[CustomKeyboard alloc] init];
    self.customKeyboard.delegate = self;
    
    CGRect screenRect = [[UIScreen mainScreen] bounds];
    CGFloat screenHeight = screenRect.size.height;
    
    if (screenHeight == 480) {
        screenSize3Point5 = YES;
    }

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dismiss
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)completePayment:(id)sender
{
    STPCard *card = [[STPCard alloc] init];
    card.number = self.cardNumber.text;
    card.expMonth = [[self.expiryDate.text substringToIndex:2] integerValue];
    card.expYear = [[self.expiryDate.text substringFromIndex:3] integerValue];
    card.cvc = self.cvv.text;
  
    void (^onCompletion)(STPToken *token, NSError *error);
    onCompletion = ^void(STPToken *token, NSError *error) {
        if (error) {
            // Failure
            NSLog(@"%@", [error localizedDescription]);
            [Alert okAlertForViewController:self withTitle:@"Payment Error" message:[error localizedDescription]];
        } else {
            // Success
            NSLog(@"%@", token.tokenId);
            // Send token to backend
            [self sendStripeTokenToBackend:token];
        }
    };
    
    [Stripe createTokenWithCard:card completion:onCompletion];
}

- (void)sendStripeTokenToBackend:(STPToken *)token
{
    
}

- (void)hideKeyboard
{
    [self.view endEditing:YES];
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

#pragma mark - TextField delegate

 - (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString *newString = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    if (textField.tag == 0) {
        return !(newString.length > 19);
    }
    
    if (textField.tag == 1) {
        if (textField.text.length == 2) {
            textField.text = [textField.text stringByAppendingString:@"/"];
        }
        
        return !(newString.length > 5);
    }
    
    if (textField.tag == 2) {
        return !(newString.length > 3);
    }
    
    return NO;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    if (screenSize3Point5 && textField.tag == 2) {
        [self scrollViewUp];
    }
    
    BOOL showPrev = textField.tag != 0;
    BOOL showNext = textField.tag != 2;
    
    [textField setInputAccessoryView:[self.customKeyboard getToolbarWithPrevNextDone:showPrev :showNext]];
    self.customKeyboard.currentSelectedTextboxIndex = textField.tag;
}

- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (textField.tag == 2) {
        [self scrollViewBackToCenter];
    }
}

- (BOOL)textFieldShouldEndEditing:(UITextField *)textField
{
    return YES;
}

#pragma mark - CustomKeyboard delegate

- (void)nextClicked:(NSUInteger)sender
{
    switch (sender) {
        case 0:
            [self.expiryDate becomeFirstResponder];
            break;
        case 1:
            [self.cvv becomeFirstResponder];
            break;
    }
}

- (void)previousClicked:(NSUInteger)sender
{
    switch (sender) {
        case 1:
            [self.cardNumber becomeFirstResponder];
            break;
        case 2:
            [self.expiryDate becomeFirstResponder];
            break;
    }
}

- (void)resignResponder:(NSUInteger)sender
{
    [self hideKeyboard];
}


@end
