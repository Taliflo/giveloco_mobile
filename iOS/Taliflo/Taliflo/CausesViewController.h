//
//  TLFCausesViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/3/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

@interface CausesViewController : UITableViewController <UISearchDisplayDelegate, UISearchBarDelegate, NSFetchedResultsControllerDelegate>

@property (nonatomic, strong) NSManagedObjectContext *managedObjectContext;

@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;

@end
