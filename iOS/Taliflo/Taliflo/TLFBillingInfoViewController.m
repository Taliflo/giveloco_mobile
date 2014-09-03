//
//  TLFBillingInfoViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFBillingInfoViewController.h"
#import "TLFColor.h"
#import "TLFNavBarHelper.h"

@interface TLFBillingInfoViewController ()

@end

@implementation TLFBillingInfoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        [TLFNavBarHelper configViewController:self withTitle:@"Update Billing Info"];
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
                                                  initWithBarButtonSystemItem:UIBarButtonSystemItemSave
                                                  target:nil
                                                  action:nil];
        [self.navigationItem.rightBarButtonItem setEnabled:NO];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    _states = @[@"AB", @"BC", @"MB", @"NB", @"NL", @"NS", @"ON", @"PE", @"QC", @"SK"];
    _countries = @[@"Canada"];
    
    // Styling
    
    // Adding close button to the various key boards
    UIToolbar *toolbar = [[UIToolbar alloc] initWithFrame:CGRectMake( 0, 0, 320, 50)];
    toolbar.barStyle = UIBarStyleDefault;
    toolbar.items = [NSArray arrayWithObjects:
                     [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil],
                     [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil],
                     [[UIBarButtonItem alloc] initWithTitle:@"Done" style:UIBarButtonItemStyleDone target:self action:@selector(closeKeyboard:)],
                     nil];
    [toolbar sizeToFit];
    
    //_name.inputAccessoryView = toolbar;
    _number.inputAccessoryView = toolbar;
    _cvv.inputAccessoryView = toolbar;
    //_street.inputAccessoryView = toolbar;
    //_city.inputAccessoryView = toolbar;
    //_zip.inputAccessoryView = toolbar;
    
    [TLFColor setStrokeTB:_name];
    [TLFColor setStrokeTB:_number];
    [TLFColor setStrokeTB:_cvv];
    [TLFColor setStrokeTB:_street];
    [TLFColor setStrokeTB:_city];
    [TLFColor setStrokeTB:_zip];
    
    [[_btnExpiryDate layer] setCornerRadius:3];
    [[_btnState layer] setCornerRadius:3];
    [[_btnCountry layer] setCornerRadius:3];
    
    [_btnState setTitle:_states[0] forState:UIControlStateNormal];
    [_btnCountry setTitle:_countries[0] forState:UIControlStateNormal];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Close any opened keyboard
- (IBAction)closeKeyboard:(id)sender
{
    [self.view endEditing:YES];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

- (IBAction)selectState:(id)sender
{
}

- (IBAction)selectCountry:(id)sender
{
    
}


@end
