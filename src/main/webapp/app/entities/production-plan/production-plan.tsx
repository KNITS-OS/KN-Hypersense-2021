import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './production-plan.reducer';
import { IProductionPlan } from 'app/shared/model/production-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionPlanProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProductionPlan = (props: IProductionPlanProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { productionPlanList, match, loading } = props;
  return (
    <div>
      <h2 id="production-plan-heading" data-cy="ProductionPlanHeading">
        Production Plans
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Production Plan
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productionPlanList && productionPlanList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Due Date</th>
                <th>Qty</th>
                <th>Name</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productionPlanList.map((productionPlan, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${productionPlan.id}`} color="link" size="sm">
                      {productionPlan.id}
                    </Button>
                  </td>
                  <td>
                    {productionPlan.dueDate ? <TextFormat type="date" value={productionPlan.dueDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{productionPlan.qty}</td>
                  <td>{productionPlan.name}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productionPlan.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productionPlan.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productionPlan.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Production Plans found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ productionPlan }: IRootState) => ({
  productionPlanList: productionPlan.entities,
  loading: productionPlan.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionPlan);
