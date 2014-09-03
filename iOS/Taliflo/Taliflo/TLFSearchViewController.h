//
//  TLFSearchController.h
//  Taliflo
//
//  Created by NR-Mac on 1/2/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TLFSearchViewController : UITableViewController <UISearchDisplayDelegate, UISearchBarDelegate>

@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;

@end
