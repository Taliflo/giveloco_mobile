//
//  TLFVoucher.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFVoucher.h"

@implementation TLFVoucher

- (instancetype)initWithJSONValues:(NSDictionary *)jsonObject
{
    self = [super init];
    
    self.claimedByID = (int) jsonObject[@"claimed_by_id"];
    self.claimedByName = (NSString *) jsonObject[@"claimed_by_name"];
    self.createdAt = (NSDate *) jsonObject[@"created_at"];
    self.ID = (int) jsonObject[@"id"];
    self.issuedByID = (int) jsonObject[@"issued_by_id"];
    //self.issuedByName = (NSString *) jsonObject[@"issued_by_name"];
    self.maxValue = (int) jsonObject[@"max_value"];
    self.redeemed = (BOOL) jsonObject[@"redeemed"];
    self.updatedAt = (NSDate *) jsonObject[@"updated_at"];
    
    return self;
}

@end
