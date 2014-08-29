//
//  TLFUserDetailViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/28/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TLFUserDetailViewController : UIViewController

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UILabel *tags;
@property (strong, nonatomic) IBOutlet UIButton *btnTransact;
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;


@end
