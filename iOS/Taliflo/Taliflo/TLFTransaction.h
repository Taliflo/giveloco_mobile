//
//  TLFTransaction.h
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFTransaction : NSObject

@property (nonatomic, copy) NSNumber * ID, *transID, *stripeID, *fromUserID, *toUserID;
@property (nonatomic, copy) NSString *transType, *fromName, *toName, *fromUserRole, *toUserRole, *amount, *status;
@property (nonatomic, copy) NSDate *cancelledAt, *completedAt, *createdAt, *updatedAt;

- (instancetype)initWithJSON:(NSDictionary *)jsonObject;

- (NSString *)formatCancelledAt;
- (NSString *)formatCompletedAt;
- (NSString *)formatCreatedAt;
- (NSString *)formateUpdatedAt;
 

@end
