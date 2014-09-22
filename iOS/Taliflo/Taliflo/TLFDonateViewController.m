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
#import <AFNetworking/AFNetworking.h>
#import <Braintree/Braintree.h>
#import "TLFUserStore.h"
#import "TLFRestHelper.h"

@interface TLFDonateViewController () <BTDropInViewControllerDelegate>
{

}

@property (nonatomic, strong) Braintree *braintree;
@property (nonatomic, strong) NSString *authToken;

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
        TLFUserStore *userStore = [TLFUserStore getInstance];
        self.authToken = userStore.authToken;
        // Set up a Braintree with session token
        NSString *clientToken = @"eyJ2ZXJzaW9uIjoxLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI0MTFlMzVmOGVlZDQwZDA0MjkyYjhmYTEyY2I5MDllYjlhNTJjYWUxYjYzOTA0MWMxYzljYjJkMTZkM2E0ODcxfGNyZWF0ZWRfYXQ9MjAxNC0wOS0yMVQwNDo0NzozMi4wODgyNTg5NzkrMDAwMFx1MDAyNm1lcmNoYW50X2lkPWRjcHNweTJicndkanIzcW5cdTAwMjZwdWJsaWNfa2V5PTl3d3J6cWszdnIzdDRuYzgiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZGNwc3B5MmJyd2RqcjNxbi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwicGF5bWVudEFwcHMiOltdLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZGNwc3B5MmJyd2RqcjNxbi9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9jbGllbnQtYW5hbHl0aWNzLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20ifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6ZmFsc2UsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6IkFjbWUgV2lkZ2V0cywgTHRkLiAoU2FuZGJveCkiLCJjbGllbnRJZCI6bnVsbCwicHJpdmFjeVVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS9wcCIsInVzZXJBZ3JlZW1lbnRVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vdG9zIiwiYmFzZVVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9jaGVja291dC5wYXlwYWwuY29tIiwiZGlyZWN0QmFzZVVybCI6bnVsbCwiYWxsb3dIdHRwIjp0cnVlLCJlbnZpcm9ubWVudE5vTmV0d29yayI6dHJ1ZSwiZW52aXJvbm1lbnQiOiJvZmZsaW5lIiwibWVyY2hhbnRBY2NvdW50SWQiOiJzdGNoMm5mZGZ3c3p5dHc1IiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn19";
        self.braintree = [Braintree braintreeWithClientToken:clientToken];
        // Create a BTDropInViewController
        BTDropInViewController *dropInVC = [self.braintree dropInViewControllerWithDelegate:self];
        // This is where you might want to customize your Drop in.
        
        // Wrap the BTDropInViewController in a new, modally presented navigation controller
        dropInVC.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel
                                                                                                  target:self
                                                                                                  action:@selector(userDidCancelPayment)];
        UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:dropInVC];
        [self presentViewController:navigationController animated:YES completion:nil];
    };
    
    // Show alert
    [TLFAlert alertForViewController:self withTitle:@"Are you sure?" message:@"Your credit card will be charged this amount immediately" yesActionHandler:yesAction cancelActionHandler:nil];
}

- (void)userDidCancelPayment
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)dropInViewController:(__unused BTDropInViewController *)viewController didSucceedWithPaymentMethod:(BTPaymentMethod *)paymentMethod
{
    NSString* nonce = paymentMethod.nonce;
    NSLog(@"Payment Nonce: %@", nonce);
    [self postNonceToServer:nonce];
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)dropInViewControllerDidCancel:(__unused BTDropInViewController *)viewController
{
    [self dismissViewControllerAnimated:YES completion:nil];
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

- (void)postNonceToServer:(NSString *)paymentMethodNonce
{
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager POST:@"http://api-dev.taliflo.com/"
       parameters:@{}
          success:^(AFHTTPRequestOperation *operation, id responseObject) {
              NSLog(@"Post Nonce to Server Success");
              [TLFAlert okAlertForViewController:self withTitle:@"Donation Complete" message:@"Thank you for using Taliflo to support this cause."];
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            NSLog(@"Post Nonce to Server Failed");
     }];
}


@end
