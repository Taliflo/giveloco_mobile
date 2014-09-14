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

@implementation TLFUser

- (instancetype)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    if (self) {
        _ID = dict[@"id"];
        _role = dict[@"role"];
        _firstName = dict[@"first_name"];
        _lastName = dict[@"last_name"];
        _phone = dict[@"phone"];
        _companyName = dict[@"company_name"];
        _streetAddress = dict[@"street_address"];
        _city = dict[@"city"];
        _state = dict[@"state"];
        _country = dict[@"country"];
        _zip = dict[@"zip"];
        _tags = dict[@"tags"];
        _summary = dict[@"summary"];
        _description = dict[@"description"];
        _website = dict[@"website"];
        _balance = dict[@"balance"];
        _totalFundsRaised = dict[@"total_funds_raised"];
        _supporters = dict[@"supporters"];
        _supportedCauses = dict[@"supported_causes"];
        //_createdAt
        //_updatedAt
        //_lastSignIn
        //_deletedAt
        _transactionsAll = [[NSMutableArray alloc] initWithArray:dict[@"transactions_created"]];
        [_transactionsAll addObjectsFromArray:dict[@"transactions_accepted"]];
    }
    return self;
}

- (int)getSupportedCausesCount
{
    if (_supportedCauses)
        return (int) self.supportedCauses.count;
    else
        return 0;
}

- (int)getSupportersCount
{
    if (_supporters)
        return (int) self.supporters.count;
    else
        return 0;
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

- (NSPredicate *)orArrayPredicate:(NSMutableArray *)arr key:(NSString *)key
{
    NSMutableArray *predArray = [[NSMutableArray alloc] init];
    for (NSObject *i in arr) {
        NSPredicate *pred = [NSPredicate predicateWithFormat:@"%K == %@", key, i];
        [predArray addObject:pred];
    }
    
    return [NSCompoundPredicate orPredicateWithSubpredicates:predArray];
}

- (NSArray *)getSupportedCauses
{
    if (![self.role isEqualToString:@"business"]) return nil;
/*
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in _supportedCauses) {
        NSPredicate *pred = [NSPredicate predicateWithFormat:@"%K == %@", @"id", i];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
*/
    NSPredicate *predicate = [self orArrayPredicate:self.supportedCauses key:@"id"];
    TLFCauseStore *store = [TLFCauseStore getInstance];
    NSArray *filtered = [store.causes filteredArrayUsingPredicate:predicate];
    return filtered;
}

- (NSArray *)getSupporters
{
    if (![self.role isEqualToString:@"cause"]) return nil;
/*
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in _supporters) {
        NSPredicate *pred = [NSPredicate predicateWithFormat:@"%K == %@", @"id", i];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
*/
    NSPredicate *predicate = [self orArrayPredicate:self.supporters key:@"id"];
    TLFBusinessStore *store = [TLFBusinessStore getInstance];
    NSArray *filtered = [store.businesses filteredArrayUsingPredicate:predicate];
    return filtered;
}

- (void)determineRedeemableBusinesses
{
    //if ([self.role isEqualToString:@"business"] || [self.role isEqualToString:@"cause"]) return;
    
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in self.supportedCauses) {
        NSPredicate *pred = [NSPredicate predicateWithBlock:^BOOL(id evaluatedObject, NSDictionary *bindings) {
            NSArray *suppCauses = evaluatedObject[@"supported_causes"];
            return [suppCauses containsObject:i];
        }];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
    TLFBusinessStore *store = [TLFBusinessStore getInstance];
    self.redeemableBusinesses = [NSMutableArray arrayWithArray:[store.businesses filteredArrayUsingPredicate:predicate]];
}

- (BOOL)checkReemableBusiness:(TLFUser *)business
{
    NSMutableArray *predArr = [[NSMutableArray alloc] init];
    for (NSNumber *i in self.supportedCauses) {
        NSPredicate *pred = [NSPredicate predicateWithBlock:^BOOL(id evaluatedObject, NSDictionary *bindings) {
            TLFUser *buss = evaluatedObject;
            NSArray *suppCauses = buss.supportedCauses;
            return [suppCauses containsObject:i];
        }];
        [predArr addObject:pred];
    }
    NSPredicate *predicate = [NSCompoundPredicate orPredicateWithSubpredicates:predArr];
    return [predicate evaluateWithObject:business];
}

@end
