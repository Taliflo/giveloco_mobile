//
//  TLFUser.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFUser.h"
#import "TLFCauseStore.h"
#import "TLFBusinessStore.h"
#import "TLFAssociation.h"

@implementation TLFUser

- (instancetype)initWithJSON:(NSDictionary *)jsonObject
{
    self = [super init];
    if (self) {
        self.ID = jsonObject[@"id"];
        self.role = jsonObject[@"role"];
        self.phone = jsonObject[@"phone"];
        self.companyName = jsonObject[@"company_name"];
        self.streetAddress = jsonObject[@"street_address"];
        self.city = jsonObject[@"city"];
        self.state = jsonObject[@"state"];
        self.country = jsonObject[@"country"];
        self.zip = jsonObject[@"zip"];
        self.tags = jsonObject[@"tags"];
        self.summary = jsonObject[@"summary"];
        self.descript = jsonObject[@"description"];
        self.website = jsonObject[@"website"];
        self.picOriginal = jsonObject[@"images"][@"profile_picture"][@"original"];
        self.picMedium = jsonObject[@"images"][@"profile_picture"][@"medium"];
        self.picThumb = jsonObject[@"images"][@"profile_picture"][@"thumb"];
        //self.createdAt = [self formatDateFromJSON:jsonObject[@"created_at"]];
        //self.updatedAt = [self formatDateFromJSON:jsonObject[@"updated_at"]];
        self.balance = jsonObject[@"balance"];
        NSNumber *isPubNum = jsonObject[@"is_published"];
        self.isPublished = [isPubNum intValue];
        
        //_lastSignIn
        //_deletedAt
        self.transactionsCreated = [[NSMutableArray alloc] initWithArray:jsonObject[@"transactions_created"]];
        self.transactionsAccepted = [[NSMutableArray alloc] initWithArray:jsonObject[@"transactions_accepted"]];
        [self sortTransactionsByDate];
        
        if ([self.role isEqualToString:@"individual"]) {
            self.firstName = jsonObject[@"first_name"];
            self.lastName = jsonObject[@"last_name"];
            self.email = jsonObject[@"email"];
            self.supportedCauses = [[NSMutableArray alloc] initWithArray:[self retrieveAssociationIdsfromJson:jsonObject[@"supported_causes"] key:@"to_user_id"]];
        }
        
        if ([self.role isEqualToString:@"business"]) {
            self.supportedCauses = [[NSMutableArray alloc] initWithArray:[self retrieveAssociationIdsfromJson:jsonObject[@"supported_causes"] key:@"to_user_id"]];
        }
        
        if ([self.role isEqualToString:@"cause"]) {
            //self.donors = jsonObject[@"donors"];
            self.supporters = [[NSMutableArray alloc] initWithArray:[self retrieveAssociationIdsfromJson:jsonObject[@"supporters"] key:@"from_user_id"]];
        }
    }
    return self;
}


/** Miscellaneous functions **/

- (NSDate *)formatDateFromJSON:(NSString *)dateString
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"YYYY-MM-dd'T'HH:mm:ssZZZ"];
    return [dateFormatter dateFromString:dateString];
}

- (void)sortTransactionsByDate
{
    NSSortDescriptor *dateDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"createdAt" ascending:NO];
    NSMutableArray *presorted = [[NSMutableArray alloc] initWithArray:self.transactionsCreated];
    [presorted addObjectsFromArray:self.transactionsAccepted];
    self.transactionsAll = [[NSMutableArray alloc] initWithArray:[presorted sortedArrayUsingDescriptors:@[dateDescriptor]]];
}

- (NSString *)getTagsString
{
    NSMutableString *string = [[NSMutableString alloc] init];
    for (NSString *tag in _tags) {
        [string appendString:@"#"];
        [string appendString:tag];
        [string appendString:@" "];
    }
    return string;
}

- (NSPredicate *)arrayORPredicate:(NSMutableArray *)anArray key:(NSString *)aKey
{
    NSMutableArray *predArray = [[NSMutableArray alloc] init];
    for (NSObject *i in anArray) {
        NSPredicate *pred = [NSPredicate predicateWithFormat:@"%K == %@", aKey, i];
        [predArray addObject:pred];
    }
    
    return [NSCompoundPredicate orPredicateWithSubpredicates:predArray];
}

- (NSArray *)retrieveAssociationIdsfromJson:(NSArray *)jsonArray key:(NSString *)aKey
{
    NSMutableArray *anArray = [[NSMutableArray alloc] init];
    for (NSDictionary *dict in jsonArray) {
        [anArray addObject:dict[aKey]];
    }
    return [anArray copy];
}

/** User functions **/

- (void)determineRedeemableBusinesses
{
    //if ([self.role isEqualToString:@"business"] || [self.role isEqualToString:@"cause"]) return;
    
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in self.supportedCauses) {
        NSPredicate *pred = [NSPredicate predicateWithBlock:^BOOL(id evaluatedObject, NSDictionary *bindings) {
            //NSArray *suppCauses = evaluatedObject[@"supported_causes"];
            NSArray *suppCauses = [evaluatedObject supportedCauses];
            return [suppCauses containsObject:i];
        }];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
    TLFBusinessStore *store = [TLFBusinessStore getInstance];
    self.redeemableBusinesses = [NSMutableArray arrayWithArray:[store.businesses filteredArrayUsingPredicate:predicate]];
}

- (BOOL)checkReemableBusiness:(TLFUser *)aBusiness
{
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in self.supportedCauses) {
        NSPredicate *pred = [NSPredicate predicateWithBlock:^BOOL(id evaluatedObject, NSDictionary *bindings) {
            //TLFUser *buss = evaluatedObject;
            NSArray *suppCauses = [evaluatedObject supportedCauses];
            return [suppCauses containsObject:i];
        }];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
    return [predicate evaluateWithObject:aBusiness];
}

/** Business functions **/

- (int)getSupportedCausesCount
{
    if (self.supportedCauses)
        return (int) self.supportedCauses.count;
    else
        return 0;
}

- (NSString *)getSupportedCausesStr
{
    int count = [self getSupportedCausesCount];
    if (count == 0) {
        return @"No Supported Causes";
    }
    else if (count == 1) {
        return @"1 Supported Cause";
    } else {
        return [NSString stringWithFormat:@"%i Supported Causes", count];
    }
}

- (NSArray *)getSupportedCauses
{
    if (![self.role isEqualToString:@"business"]) return nil;

    NSPredicate *predicate = [self arrayORPredicate:self.supportedCauses key:@"ID"];
    TLFCauseStore *store = [TLFCauseStore getInstance];
    NSArray *filtered = [store.causes filteredArrayUsingPredicate:predicate];
    return filtered;
}

/** Cause functions **/

- (int)getSupportersCount
{
    if (self.supporters)
        return (int) self.supporters.count;
    else
        return 0;
}

- (NSString *)getSupportersCountStr
{
    int count = [self getSupportersCount];
    if (count == 0) {
        return @"No Supporters";
    } else if (count == 1) {
        return @"1 Supporter";
    } else {
        return [NSString stringWithFormat:@"%i Supporters", count];
    }
}

- (NSArray *)getSupporters
{
    if (![self.role isEqualToString:@"cause"]) return nil;

    NSPredicate *predicate = [self arrayORPredicate:self.supporters key:@"ID"];
    TLFBusinessStore *store = [TLFBusinessStore getInstance];
    NSArray *filtered = [store.businesses filteredArrayUsingPredicate:predicate];
    return filtered;
}

@end
