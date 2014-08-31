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
        
        TLFNavBarHelper *nbHelper = [TLFNavBarHelper getInstance];
        [nbHelper configViewController:self withTitle:@"Update Billing Info"];
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
    [_dropDown fadeOut];
    [self showPopUpWithTitle:@"Select Province" withOption:_states xy:CGPointMake(16, 78) size:CGSizeMake(287, 330) isMultiple:NO];
}

- (IBAction)selectCountry:(id)sender
{
    
}

- (void)showPopUpWithTitle:(NSString*)popupTitle withOption:(NSArray*)arrOptions xy:(CGPoint)point size:(CGSize)size isMultiple:(BOOL)isMultiple
{
    _dropDown = [[DropDownListView alloc] initWithTitle:popupTitle options:arrOptions xy:point size:size isMultiple:isMultiple];
    _dropDown.delegate = self;
    [_dropDown showInView:self.view animated:YES];
    
    // Set background colour
    [_dropDown SetBackGroundDropDwon_R:0 G:167.0 B:157 alpha:1];
}

- (void)DropDownListView:(DropDownListView *)dropdownListView didSelectedIndex:(NSInteger)anIndex
{
    [_btnState setTitle:_states[anIndex] forState:UIControlStateNormal];
}

- (void)DropDownListView:(DropDownListView *)dropdownListView Datalist:(NSMutableArray *)ArryData
{
}

- (void)DropDownListViewDidCancel
{
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    UITouch *touch = [touches anyObject];
    
    if ([touch.view isKindOfClass:[UIView class]]) {
        [_dropDown fadeOut];
    }
}

-(CGSize)GetHeightDyanamic:(UILabel*)lbl
{
    NSRange range = NSMakeRange(0, [lbl.text length]);
    CGSize constraint;
    constraint= CGSizeMake(288 ,MAXFLOAT);
    CGSize size;
    
    if (([[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0)) {
        NSDictionary *attributes = [lbl.attributedText attributesAtIndex:0 effectiveRange:&range];
        CGSize boundingBox = [lbl.text boundingRectWithSize:constraint options: NSStringDrawingUsesLineFragmentOrigin attributes:attributes context:nil].size;
        
        size = CGSizeMake(ceil(boundingBox.width), ceil(boundingBox.height));
    }
    else{
        NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
        paragraphStyle.lineBreakMode = NSLineBreakByWordWrapping;
        
        size = [lbl.text sizeWithFont:[UIFont fontWithName:@"Helvetica" size:14] constrainedToSize:constraint lineBreakMode:NSLineBreakByWordWrapping];
    }
    return size;
}
@end
