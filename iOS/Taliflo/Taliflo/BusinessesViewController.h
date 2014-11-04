//
//  TLFBusinessesViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/3/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

@interface BusinessesViewController : UITableViewController <UISearchDisplayDelegate, UISearchBarDelegate, NSFetchedResultsControllerDelegate>

@property (nonatomic, strong) NSManagedObjectContext *managedObjectContext;

@property (strong, nonatomic) IBOutlet UISearchBar *searchBar;

@end
