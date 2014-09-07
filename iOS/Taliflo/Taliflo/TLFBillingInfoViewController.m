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
//#import "ActionSheetStringPicker.h"

@interface TLFBillingInfoViewController ()

@property (nonatomic, strong) NSArray *months, *years, *states, *countries;
@property (nonatomic) NSInteger expireMonth, expireYear;

@end

static NSInteger maxYear = 2030;
static NSInteger minYear;
static NSInteger currentMonth;

@implementation TLFBillingInfoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        self.navigationItem.title = @"Update Billing Info";
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
                                                  initWithTitle:@"Done"
                                                  style:UIBarButtonItemStylePlain
                                                  target:nil
                                                  action:@selector(saveInfo)];
        [self.navigationItem.rightBarButtonItem setEnabled:YES];

    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Initialize variables
    
    NSDate *currentDate = [NSDate date];
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [calendar components:NSYearCalendarUnit|NSMonthCalendarUnit fromDate:currentDate];
    minYear = [components year];
    currentMonth = [components month];
    
    self.months = @[@"January", @"February", @"March", @"April", @"May", @"June", @"July", @"August", @"September", @"October", @"November", @"December"];
    self.years = [self setYears];
    
    self.states = @[@"AB", @"BC", @"MB", @"NB", @"NL", @"NS", @"ON", @"PE", @"QC", @"SK"];
    self.countries = @[@"Canada"];
    
    // ** Styling **
    
    // Adding close button to the various key board
    self.number.inputAccessoryView = [self doneToolbarWithTitle:@"Enter credit card number"];
    self.cvv.inputAccessoryView = [self doneToolbarWithTitle:@"Enter your CVV"];
    self.street.inputAccessoryView = [self doneToolbarWithTitle:@"Select expiry date"];
    
    [TLFColor setStrokeTB:self.name];
    [TLFColor setStrokeTB:self.number];
    [TLFColor setStrokeTB:self.cvv];
    [TLFColor setStrokeTB:self.street];
    [TLFColor setStrokeTB:self.city];
    [TLFColor setStrokeTB:self.zip];
    
    [[self.btnExpiryDate layer] setCornerRadius:3];
    
    [self.btnState setTitle:self.states[0] forState:UIControlStateNormal];
    [self.btnCountry setTitle:self.countries[0] forState:UIControlStateNormal];
    
    [[self.btnExpiryDate layer] setCornerRadius:3];
    [[self.btnState layer] setCornerRadius:3];
    [[self.btnCountry layer] setCornerRadius:3];
    
    UIPickerView *pickerExpiryDate = [[UIPickerView alloc] init];
    pickerExpiryDate.dataSource = self;
    pickerExpiryDate.delegate = self;
    self.street.inputView = pickerExpiryDate;
    // Select the current month
    [pickerExpiryDate selectRow:(currentMonth - 1) inComponent:0 animated:NO];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
    [self.scrollView setContentSize:CGSizeMake(320, 565)];
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
/*    [ActionSheetStringPicker showPickerWithTitle:@"Select a Province"
                                            rows:_states initialSelection:0
                                       doneBlock:^(ActionSheetStringPicker *picker, NSInteger selectedIndex, id selectedValue) {
                                           [self.btnState setTitle:_states[selectedIndex] forState:UIControlStateNormal];
                                       }
                                     cancelBlock:^(ActionSheetStringPicker *picker) {
                                         
                                     }
                                          origin:sender]; */
}

- (IBAction)selectCountry:(id)sender
{

}

- (IBAction)saveInfo
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"These changes will be reflected on your accout immediately." delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
    [alert show];
    
    // To be used on iOS 8 for backwards compatability
    /*    if ([UIAlertController class]) {
     
     } else {
     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"These changes will be reflected on your accout immediately." delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
     [alert show];
     } */
}

// Method to respond to the response from the alert view
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) NSLog(@"Cancel button clicked");
    if (buttonIndex == 1) NSLog(@"Yes button clicked");
}

- (UIToolbar *)doneToolbarWithTitle:(NSString *)title
{
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 180, 30)];
    [label setTextAlignment:NSTextAlignmentLeft];
    [label setTextColor:[UIColor blackColor]];
    [label setFont:[UIFont boldSystemFontOfSize:15]];
    [label setBackgroundColor:[UIColor clearColor]];
    label.text = title;
    
    UIToolbar *toolbar = [[UIToolbar alloc] initWithFrame:CGRectMake( 0, 0, 320, 50)];
    toolbar.barStyle = UIBarStyleDefault;
    toolbar.items = [NSArray arrayWithObjects:
                     [[UIBarButtonItem alloc] initWithCustomView:label],
                     [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil],
                     [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(closeKeyboard:)],
                     nil];
    [toolbar sizeToFit];
    
    return toolbar;
}

- (NSArray *)setYears
{
    NSMutableArray *years = [NSMutableArray array];
    
    for (NSInteger i = minYear; i <= maxYear; i++) {
        NSString *str = [NSString stringWithFormat:@"%li", (long)i];
        [years addObject:str];
    }
    
    return [NSArray arrayWithArray:years];
}

#pragma mark - Picker view delegate

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 2;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component == 0)
        return [self.months count];
    else
        return [self.years count];
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if (component == 0)
        return self.months[row];
    else
        return self.years[row];
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    // Set the expiry date to the selected rows
    if (component == 0) self.expireMonth = row + 1;
    if (component == 1) self.expireYear = [self.years[row] integerValue];
    NSLog(@"Expiry date: %i/%i", self.expireMonth, self.expireYear);
}



@end
