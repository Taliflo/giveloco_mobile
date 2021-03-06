//
//  TLFBillingInfoViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "BillingInfoViewController.h"
#import "CustomColor.h"
#import "NavigationBarHelper.h"
#import "TranslateViewHelper.h"

@interface BillingInfoViewController ()

@property (nonatomic, strong) NSArray *months, *years, *states, *countries;
@property (nonatomic) NSInteger expireMonth, expireYear;
@property (nonatomic, strong) UIPickerView *pickerExpiryDate, *pickerState, *pickerCountry;
@property (nonatomic, strong) CustomKeyboard *customKeyboard;

@end

static NSInteger maxYear = 2030;
static NSInteger minYear;
static NSInteger currentMonth;
static TranslateViewHelper *viewHelper;

@implementation BillingInfoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        self.navigationItem.title = @"Update Billing Info";
        /*self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
                                                  initWithTitle:@"Save"
                                                  style:UIBarButtonItemStylePlain
                                                  target:nil
                                                  action:@selector(saveInfo)]; */
        
        //[self.navigationItem.rightBarButtonItem setEnabled:YES];
        viewHelper = [[TranslateViewHelper alloc] initWithViewController:self];
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
    
    // Picker view data source
    self.months = @[@"January", @"February", @"March", @"April", @"May", @"June", @"July", @"August", @"September", @"October", @"November", @"December"];
    self.years = [self setYears];
    self.states = @[@"AB", @"BC", @"MB", @"NB", @"NL", @"NS", @"ON", @"PE", @"QC", @"SK"];
    self.countries = @[@"Canada"];
    
    // ** Styling **
    
    // Keyboard toolbar
    self.customKeyboard = [[CustomKeyboard alloc] init];
    self.customKeyboard.delegate = self;
    
    [CustomColor setStrokeTB:self.name];
    [CustomColor setStrokeTB:self.number];
    [CustomColor setStrokeTB:self.expiryDate];
    [CustomColor setStrokeTB:self.cvv];
    [CustomColor setStrokeTB:self.street];
    [CustomColor setStrokeTB:self.city];
    [CustomColor setStrokeTB:self.state];
    [CustomColor setStrokeTB:self.country];
    [CustomColor setStrokeTB:self.zip];
    
    [self.state setText:self.states[0]];
    [self.country setText:self.countries[0]];
    
    // Initialize pickers
    self.pickerExpiryDate = [[UIPickerView alloc] init];
    self.pickerExpiryDate.dataSource = self;
    self.pickerExpiryDate.delegate = self;
    self.pickerExpiryDate.tag = 0;
    self.expiryDate.inputView = self.pickerExpiryDate;
    // Set the current month as the default selection
    [self.pickerExpiryDate selectRow:(currentMonth - 1) inComponent:0 animated:NO];
    
    self.pickerState = [[UIPickerView alloc] init];
    self.pickerState.dataSource = self;
    self.pickerState.delegate = self;
    self.pickerState.tag = 1;
    self.state.inputView = self.pickerState;
    
    self.pickerCountry = [[UIPickerView alloc] init];
    self.pickerCountry.dataSource = self;
    self.pickerCountry.delegate = self;
    self.pickerCountry.tag = 2;
    self.country.inputView = self.pickerCountry;
    
    [[self.btnSave layer] setCornerRadius:3];
    self.btnSave.enabled = NO;
    self.btnSave.backgroundColor = [CustomColor disabledGrey];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
    
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

- (IBAction)saveInfo
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?" message:@"These changes will be reflected on your accout immediately." delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"Yes", nil];
    [alert show];

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
    if (pickerView.tag == 0)
        return 2;
    else
        return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    switch (pickerView.tag) {
        case 0:
            if (component == 0)
                return [self.months count];
            else
                return [self.years count];
            break;
            
        case 1:
            return [self.states count];
            break;
            
        case 2:
            return [self.countries count];
            break;
            
        default:
            return 0;
            break;
    }

}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    switch (pickerView.tag) {
        case 0:
            if (component == 0)
                return self.months[row];
            else
                return self.years[row];
            break;
            
        case 1:
            return self.states[row];
            break;
        
        case 2:
            return self.countries[0];
            break;
            
        default:
            return @"";
            break;
    }

}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{

    switch (pickerView.tag) {
        case 0:
            // Set the expiry date to the selected rows
            if (component == 0) {
                self.expireMonth = row + 1;
            }
            if (component == 1){
                self.expireYear = [self.years[row] integerValue];
            }
            
            if (self.expireYear == 0) {
                NSDate *currentDate = [NSDate date];
                NSCalendar *calendar = [NSCalendar currentCalendar];
                NSDateComponents *components = [calendar components:NSYearCalendarUnit fromDate:currentDate];
                self.expireYear = [components year];
            }
            
            NSLog(@"Expiry date: %li/%li", (long)self.expireMonth, (long)self.expireYear-2000);
            [self.expiryDate setText:[NSString stringWithFormat:@"%li/%li", (long)self.expireMonth, (long)self.expireYear-2000]];
            break;
            
        case 1:
            [self.state setText:self.states[row]];
            break;
            
        case 2:
            break;
    }

}

#pragma mark - Text field delegate

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    NSLog(@"TextField began editing.");
    
    BOOL showPrev = textField.tag != 0;
    BOOL showNext = textField.tag != 8;
    
    [textField setInputAccessoryView:[self.customKeyboard getToolbarWithPrevNextDone:showPrev :showNext]];
    self.customKeyboard.currentSelectedTextboxIndex = textField.tag;

    NSLog(@"Textfield origin.y: %f", textField.frame.origin.y);
    
    [viewHelper scrollViewUpForKeyboard:textField];
}

- (BOOL)textFieldShouldEndEditing:(UITextField *)textField
{
    [viewHelper scrollViewBackToCenter];
    return YES;
}

#pragma mark - Custom keyboard delegate

- (void)nextClicked:(NSUInteger)sender
{
    switch (sender) {
        case 0:
            [self.number becomeFirstResponder];
            break;
        case 1:
            [self.expiryDate becomeFirstResponder];
            break;
        case 2:
            [self.cvv becomeFirstResponder];
            break;
        case 3:
            [self.street becomeFirstResponder];
            break;
        case 4:
            [self.city becomeFirstResponder];
            break;
        case 5:
            [self.state becomeFirstResponder];
            break;
        case 6:
            [self.country becomeFirstResponder];
            break;
        case 7:
            [self.zip becomeFirstResponder];
            break;
    }
}

- (void)previousClicked:(NSUInteger)sender
{
    switch (sender) {
        case 1:
            [self.name becomeFirstResponder];
            break;
        case 2:
            [self.number becomeFirstResponder];
            break;
        case 3:
            [self.expiryDate becomeFirstResponder];
            break;
        case 4:
            [self.cvv becomeFirstResponder];
            break;
        case 5:
            [self.street becomeFirstResponder];
            break;
        case 6:
            [self.city becomeFirstResponder];
            break;
        case 7:
            [self.state becomeFirstResponder];
            break;
        case 8:
            [self.country becomeFirstResponder];
            break;
    }
}

- (void)resignResponder:(NSUInteger)sender
{
    [self closeKeyboard:nil];
}

@end
