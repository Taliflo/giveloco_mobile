//
//  TLFUser.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFUser.h"

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
        _transactions = [[NSMutableArray alloc] initWithArray:dict[@"transactions_created"]];
        [_transactions addObjectsFromArray:dict[@"transactions_accepted"]];
    }
    return self;
}

- (int)getSupportedCausesCount
{
    return (int)_supportedCauses.count;
}

- (int)getSupportersCount
{
    return (int)_supporters.count;
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

@end
