//
//  Transaction.m
//  taliflo
//
//  Created by Ntokozo Radebe on 2014-11-04.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "Transaction.h"
#import "User.h"


@implementation Transaction

@dynamic transactionID;
@dynamic stripeID;
@dynamic fromUserID;
@dynamic toUserID;
@dynamic transactionType;
@dynamic fromName;
@dynamic toName;
@dynamic fromUserRole;
@dynamic toUserRole;
@dynamic amount;
@dynamic status;
@dynamic cancelledAt;
@dynamic completedAt;
@dynamic createdAt;
@dynamic updatedAt;
@dynamic users;

@end
