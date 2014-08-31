//
//  TLFBillingInfoViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/30/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DropDownListView.h"

@interface TLFBillingInfoViewController : UIViewController<kDropDownListViewDelegate> {
    NSArray *_states, *_countries;
    DropDownListView *_dropDown;
}

@property (strong, nonatomic) IBOutlet UITextField *name;
@property (strong, nonatomic) IBOutlet UITextField *number;
@property (strong, nonatomic) IBOutlet UITextField *cvv;
@property (strong, nonatomic) IBOutlet UIButton *btnExpiryDate;
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) IBOutlet UITextField *street;
@property (strong, nonatomic) IBOutlet UITextField *city;
@property (strong, nonatomic) IBOutlet UIButton *btnState;
@property (strong, nonatomic) IBOutlet UIButton *btnCountry;
@property (strong, nonatomic) IBOutlet UITextField *zip;

@end
