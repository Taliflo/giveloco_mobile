//
//  TLFTransaction.h
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFTransaction : NSObject

@property (nonatomic, copy) NSString * ID, *transID, *stripeID, *transType, *fromUserID, *toUserID, *fromName, *toName, *fromUserRole, *toUserRole, *amount, *status;
@property (nonatomic, strong) NSDate *cancelledAt, *completedAt, *createdAt, *updatedAt;

- (NSString *)formatCancelledAt;
- (NSString *)formatCompletedAt;
- (NSString *)formatCreatedAt;
- (NSString *)formateUpdatedAt;

@end
