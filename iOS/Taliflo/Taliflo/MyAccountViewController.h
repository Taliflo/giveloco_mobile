//
//  TLFMyAccountViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

@interface MyAccountViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) NSManagedObjectContext *managedObjectContext;

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UILabel *balance;
@property (strong, nonatomic) IBOutlet UITableView *tableView;

@end
