//
//  TLFAssociation.m
//  Taliflo
//
//  Created by NR-Mac on 1/7/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFAssociation.h"

@implementation TLFAssociation

- (instancetype)initWithJSON:(NSDictionary *)jsonObject
{
    self = [super init];
    if (self) {
        self.connectionId = jsonObject[@"connection_id"];
        self.fromName = jsonObject[@"from_name"];
        self.toName = jsonObject[@"to_name"];
        self.fromUserId = jsonObject[@"from_user_id"];
        self.toUserId = jsonObject[@"to_user_id"];
        self.transactions = jsonObject[@"transactions"];
    }
    return self;
}

@end
