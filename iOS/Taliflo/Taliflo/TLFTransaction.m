//
//  TLFTransaction.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFTransaction.h"

@interface TLFTransaction ()

@end

@implementation TLFTransaction

static NSDateFormatter *_inFormatter;
static NSDateFormatter *_outFormatter;

- (instancetype)initWithDictionary:(NSDictionary *)dict
{
    self = [super init];
    
    if (_inFormatter == nil) {
        _inFormatter = [[NSDateFormatter alloc] init];
        [_inFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSSZ"];
    }
    
    if (_outFormatter == nil) {
        _outFormatter = [[NSDateFormatter alloc] init];
        _outFormatter.dateStyle = NSDateFormatterShortStyle;
        _outFormatter.timeStyle = NSDateFormatterNoStyle;
    }
    
    if (self) {
        _ID = dict[@"id"];
        _transID = dict[@"trans_id"];
        _stripeID = dict[@"stripe_id"];
        _transType = dict[@"trans_type"];
        _fromUserID = dict[@"from_user_id"];
        _toUserID = dict[@"to_user_id"];
        _fromName = dict[@"from_name"];
        _toName = dict[@"to_name"];
        _fromUserRole = dict[@"from_user_role"];
        _toUserRole = dict[@"to_user_role"];
        _amount = dict[@"amount"];
        _status = dict[@"status"];
        
        _createdAt = [_inFormatter dateFromString:dict[@"created_at"]];
        _updatedAt = [_inFormatter dateFromString:dict[@"updated_at"]];
    }
    

    
    return self;
}

- (NSString *)formatCancelledAt
{
    return [_outFormatter stringFromDate:_cancelledAt];
}

- (NSString *)formatCompletedAt
{
    return [_outFormatter stringFromDate:_completedAt];
}

- (NSString *)formatCreatedAt
{
    return [_outFormatter stringFromDate:_createdAt];
}

- (NSString *)formateUpdatedAt
{
    return [_outFormatter stringFromDate:_updatedAt];
}

@end
