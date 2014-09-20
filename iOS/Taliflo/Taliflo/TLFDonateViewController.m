//
//  TLFDonateViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFDonateViewController.h"
#import "TLFUser.h"
#import "TLFNavBarHelper.h"
#import "TLFAlert.h"

@interface TLFDonateViewController () {

}

@end

@implementation TLFDonateViewController

static NSString *donationAmount;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Set view controller title
        //[TLFNavBarHelper configViewController:self withTitle:@"Donate"];
        self.title = @"Donate Now";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Populate views
    self.name.text = self.cause.companyName;
    
    // Styling
    [[self.btnDonate layer] setCornerRadius:3];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark Action listeners

- (IBAction)selectDonation
{
    if (_segmentedControl.selectedSegmentIndex == 0) {
        // Selected $20
        donationAmount = @"20.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 1) {
        // Selected $40
        donationAmount = @"40.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 2) {
        // Selected $60
        donationAmount = @"60.00";
    }
    
    if (_segmentedControl.selectedSegmentIndex == 3) {
        // Selected $100
        donationAmount = @"100.00";
    }
}

- (IBAction)confirm
{
    // Set the confirm action
    void(^yesAction)(UIAlertAction *action);
    yesAction = ^void(UIAlertAction *action) {
        NSLog(@"\"Yes\" selected");
        
        NSString *descript = [NSString stringWithFormat:@"Donation to %@ using the Taliflo platform", self.name.text];
        
        PKPaymentRequest *request = [Stripe paymentRequestWithMerchantIdentifier:@"merchant.com.taliflo"
                                                                          amount:[NSDecimalNumber decimalNumberWithString:donationAmount]
                                                                        currency:@"CAD"
                                                                     description:descript];
        
        if ([Stripe canSubmitPaymentRequest:request]) {
            UIViewController *paymentController = [Stripe paymentControllerWithRequest:request delegate:self];
            [self presentViewController:paymentController animated:YES completion:nil];
        } else {
            // Show the user your own credit card form
            
        }
    };
    
    // Show alert
    [TLFAlert alertForViewController:self withTitle:@"Are you sure?" message:@"Your credit card will be charged this amount immediately" yesActionHandler:yesAction cancelActionHandler:nil];
}

// Method to respond to the response from the alert view
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        NSLog(@"Cancel button clicked");
    }
    
    if (buttonIndex == 1) {
        NSLog(@"Yes button clicked");
    }
}

- (void)paymentAuthorizationViewController:(PKPaymentAuthorizationViewController *)controller
                       didAuthorizePayment:(PKPayment *)payment
                                completion:(void (^)(PKPaymentAuthorizationStatus))completion
{
    /*
     The block that takes a PKPaymentAuthorizationStatus is called with either PKPaymentAuthorizationStatusSuccess or PKPaymentAuthorizationStatusFailure
     after the asynchronous code finishes executing. This notifies the view controller to update its UI.
     */
    [self handlePaymentAuthorizationWithPayment:payment completion:completion];
}

- (void)paymentAuthorizationViewControllerDidFinish:(PKPaymentAuthorizationViewController *)controller
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

/*
 CREATING TOKENS
 
 The Stripe libraries send credit card data directly to the Stripe servers, where this data is converted into tokens.
 You can charge thses tokens later in your server-side code.
*/
- (void)handlePaymentAuthorizationWithPayment:(PKPayment *)payment completion:(void (^)(PKPaymentAuthorizationStatus))completion
{
    
    [Stripe createTokenWithPayment:payment completion:^(STPToken *token, NSError *error) {
        if (error) {
            completion(PKPaymentAuthorizationStatusFailure);
            return;
        }
        
        [self createBackendChargeWithToken:token completion:completion];
    }];
}

// Send the token to your server
- (void)createBackendChargeWithToken:(STPToken *)token completion:(void (^)(PKPaymentAuthorizationStatus))completion
{
    NSURL *url = [NSURL URLWithString:@"http://example.com/token"];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:url];
    request.HTTPMethod = @"POST";
    NSString *body = [NSString stringWithFormat:@"stripeToken=%@", token.tokenId];
    request.HTTPBody = [body dataUsingEncoding:NSUTF8StringEncoding];
    
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:[NSOperationQueue mainQueue]
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *connectionError) {
                               if (connectionError) {
                                   completion(PKPaymentAuthorizationStatusFailure);
                               } else {
                                   completion(PKPaymentAuthorizationStatusSuccess);
                               }
                           }];
}

@end
